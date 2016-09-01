#include <stdio.h>
#include <stdint.h>
#include <jni.h>
#include <unistd.h>
#include <sys/stat.h>
#include <android/log.h>
#include <android/native_window.h> // requires ndk r5 or newer
#include <android/native_window_jni.h> // requires ndk r5 or newer
#include <android/asset_manager_jni.h>

#include "AndroidSDK.h"
#include <RenderControl/AndroidSDKImpl.h>
#include <RenderControl/AndroidSDKImpl_Fde.h>

#include <vector>
#include <string>
#include <iostream>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdlib.h>

#include <gvImage/ReadWriteImagePAD.h>

#include <gfx/ConvertUTF.h>


#ifndef	__ANDROID__
#define	__ANDROID__	1
#endif

#define LOG_TAG "GvitechAndroidSDK"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)


ANativeWindow*	g_pNativeWindow = 0;
AAssetManager* g_pAssetManager = 0;

JavaVM* gs_jvm = NULL;
jobject gs_object = NULL;


std::string getSTLStringA(JNIEnv* jenv, jstring& jstr)
{
    char* strBuffer = (char*)jenv->GetStringUTFChars(jstr, 0);
	std::string strArray = strBuffer;
    
	jenv->ReleaseStringUTFChars(jstr, strBuffer);
	jenv->DeleteLocalRef(jstr);
    
	return	strArray;
}

std::wstring getSTLString(JNIEnv *env, const jstring jStr)
{
    int len = env->GetStringLength(jStr);
	std::wstring strRet(len, 0);
    
    const jchar* p = env->GetStringChars(jStr, 0);
	if (p == 0)
	{
		env->DeleteLocalRef(jStr);
		return L"";
	}
    int i;
	for (i = 0; i < len; i++)
		strRet[i] = p[i];
    
	env->ReleaseStringChars(jStr, p);
	env->DeleteLocalRef(jStr);
    
	return	strRet;
}

jstring getJavaString(JNIEnv *env, const std::wstring& str)
{
    jchar* p = (jchar*)malloc(sizeof(wchar_t) * str.size());
    int i;
	for (i = 0; i < str.size(); i++)
		p[i] = str[i];
	p[i] = 0;
    
	jstring strRet = env->NewString(p, str.size());
	free(p);
    
    return strRet;
}

jstring getJavaStringA(JNIEnv* env, const char* str)
{
	const char* pStr = str;
	int        strLen    = strlen(pStr);
    jclass     jstrObj   = env->FindClass("java/lang/String");
    jmethodID  methodId  = env->GetMethodID(jstrObj, "<init>", "([BLjava/lang/String;)V");
    jbyteArray byteArray = env->NewByteArray(strLen);
    jstring    encode    = env->NewStringUTF("utf-8");
    
    env->SetByteArrayRegion(byteArray, 0, strLen, (jbyte*)pStr);
    
	jstring jstr = (jstring)env->NewObject(jstrObj, methodId, byteArray, encode);
    
	env->DeleteLocalRef(jstrObj);
	//env->DeleteLocalRef(methodId);
	env->DeleteLocalRef(byteArray);
	env->DeleteLocalRef(encode);
    
    return jstr;
}

/**********************************************************
 * 默认是不按顺序获取，如是从0开始顺序获取，则把第三个参数置为true
 *********************************************************/
int getEnumValue(JNIEnv* env, jobject enumObj, bool bGetOrdinal = false, int nDefault = 0)
{
    int nRet = nDefault;
    jclass enumclass = env->GetObjectClass(enumObj);
    if(enumclass != NULL)
    {
        jmethodID me = env->GetMethodID(enumclass, bGetOrdinal? "ordinal" : "getValue", "()I");
        jint val = env->CallIntMethod(enumObj, me);
        nRet = val;
    }
    
    return nRet;
}


/**********************************************************
 * RenderControl
 *********************************************************/
JNIEXPORT jboolean JNICALL Java_com_gvitech_android_RenderControl_initializeT(JNIEnv* env, jobject obj, jobject surface, jobject assetManager)
{
    //2014.12.09 用于事件回调
    env->GetJavaVM(&gs_jvm);
    gs_object = env->NewGlobalRef(obj);
    
	g_pNativeWindow = ANativeWindow_fromSurface(env, surface);
	int nWidth = ANativeWindow_getWidth(g_pNativeWindow);
	int nHeight = ANativeWindow_getHeight(g_pNativeWindow);
    
	g_pAssetManager = AAssetManager_fromJava(env, assetManager);
    gvs::SetAAssetManager(static_cast<AAssetManager*>(g_pAssetManager));
    
	env->DeleteLocalRef(surface);
	env->DeleteLocalRef(assetManager);
	return	sdk::RenderControl::Initialize(g_pNativeWindow, nWidth, nHeight);
}
JNIEXPORT jboolean JNICALL Java_com_gvitech_android_RenderControl_initialize2T(JNIEnv* env, jobject obj, jobject surface, jobject assetManager, jstring wkt)
{
    //2014.12.09 用于事件回调
    env->GetJavaVM(&gs_jvm);
    gs_object = env->NewGlobalRef(obj);
    
    g_pNativeWindow = ANativeWindow_fromSurface(env, surface);
    int nWidth = ANativeWindow_getWidth(g_pNativeWindow);
    int nHeight = ANativeWindow_getHeight(g_pNativeWindow);
    
    g_pAssetManager = AAssetManager_fromJava(env, assetManager);
    gvs::SetAAssetManager(static_cast<AAssetManager*>(g_pAssetManager));
    
    env->DeleteLocalRef(surface);
    env->DeleteLocalRef(assetManager);
    
    std::wstring strWkt = getSTLString(env, wkt);
    return	sdk::RenderControl::Initialize2(g_pNativeWindow, nWidth, nHeight, strWkt);
}
JNIEXPORT jboolean JNICALL Java_com_gvitech_android_RenderControl_uninitialize(JNIEnv* env, jobject obj)
{
	ANativeWindow_release(g_pNativeWindow);
	return	sdk::RenderControl::Uninitialize();
}
JNIEXPORT jboolean JNICALL Java_com_gvitech_android_RenderControl_resetView(JNIEnv* env, jobject obj, jobject surface)
{
	int nWidth = ANativeWindow_getWidth(g_pNativeWindow);
	int nHeight = ANativeWindow_getHeight(g_pNativeWindow);
    
	env->DeleteLocalRef(surface);
	return	sdk::RenderControl::ResetView(nWidth, nHeight);
}


JNIEXPORT jboolean JNICALL Java_com_gvitech_android_RenderControl_pauseRendering(JNIEnv* env, jobject obj, jboolean dumpMemory)
{
	return	sdk::RenderControl::PauseRendering(dumpMemory);
}

JNIEXPORT jboolean JNICALL Java_com_gvitech_android_RenderControl_resumeRendering(JNIEnv* env, jobject obj)
{
	return	sdk::RenderControl::ResumeRendering();
}

JNIEXPORT jboolean JNICALL Java_com_gvitech_android_RenderControl_reset(JNIEnv* env, jobject obj, jobject surface, jboolean isPlanarTerrain)
{
	return	sdk::RenderControl::Reset(isPlanarTerrain);
}

 
JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_nativeTouchEvent(JNIEnv* env, jobject obj, jint touchCount, jintArray touchPosArray)
{
	jint* touchPos = env->GetIntArrayElements(touchPosArray, 0);
    
	sdk::RenderControl::ParserTouchInputInfo(0x0240, (void*) touchCount, (void*) touchPos);
    
	env->ReleaseIntArrayElements(touchPosArray, touchPos, 0);
	env->DeleteLocalRef(touchPosArray);
}

JNIEXPORT jboolean JNICALL Java_com_gvitech_android_RenderControl_enterWalkMode(JNIEnv* env, jobject obj, jstring roadSdb, jstring password)
{
    std::wstring strRoad = getSTLString(env, roadSdb);
    std::wstring strPass = getSTLString(env, password);
    return sdk::RenderControl::EnterWalkMode(strRoad, strPass);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_RenderControl_getInteractModeT(JNIEnv* env, jobject obj)
{
    return sdk::RenderControl::GetInteractMode();
}
JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_setInteractMode(JNIEnv* env, jobject obj, jobject newVal)
{
    int nVal = getEnumValue(env, newVal);
    sdk::RenderControl::SetInteractMode(nVal);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_RenderControl_getMouseSelectModeT(JNIEnv* env, jobject obj)
{
    return sdk::RenderControl::GetMouseSelectMode();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_setMouseSelectMode(JNIEnv* env, jobject obj, jobject newVal)
{
    int nVal = getEnumValue(env, newVal);
    sdk::RenderControl::SetMouseSelectMode(nVal);

}

JNIEXPORT jint JNICALL Java_com_gvitech_android_RenderControl_getMouseSelectObjectMask(JNIEnv* env, jobject obj)
{
    return sdk::RenderControl::GetMouseSelectObjectMask();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_setMouseSelectObjectMask(JNIEnv* env, jobject obj, jint newVal)
{
    sdk::RenderControl::SetMouseSelectObjectMask(newVal);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_RenderControl_getMeasurementModeT(JNIEnv* env, jobject obj)
{
    return sdk::RenderControl::GetMeasurementMode();
}
JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_setMeasurementMode(JNIEnv* env, jobject obj, jobject newVal)
{
    int nVal = getEnumValue(env, newVal);
    sdk::RenderControl::SetMeasurementMode(nVal);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_sensorChanged(JNIEnv* env, jobject obj, jdouble roll, jdouble pitch, jdouble yaw)
{
    sdk::RenderControl::SensorChanged(roll, pitch, yaw);
}

JNIEXPORT jdouble JNICALL Java_com_gvitech_android_RenderControl_internalTest(JNIEnv* env, jobject obj, jstring p1, jstring p2)
{
    LOGD("----enter test------");
    jdouble r = 0.0;
    
    std::string strFile = getSTLStringA(env, p1);
    LOGD(strFile.c_str());
    
    //FILE* fp = fopen(strFile.c_str(), "r+");
    int fp = open(strFile.c_str(), O_RDONLY, S_IRUSR);
    if(fp >= 0)
    {
        
        //unsigned long filesize = -1;
        //struct stat statbuff;
        //if(stat(strFile.c_str(), &statbuff) >= 0)
        //    filesize = statbuff.st_size;
        //LOGD("stat file size: %ld", filesize);
        
        //char buff[20];
        //int nRead = fread(buff, 1, 20, fp);
        //LOGD("---fread: %d", nRead);
        
        //int nsek = fseek(fp, 0L, SEEK_END);
        //LOGD("---fseek: %d, ftell: %ld", nsek, ftell(fp));

        
        LOGD("---open file OK");
        r = lseek64(fp, 0, SEEK_END);
        LOGD("---lseek64: %f", r);
        
        //fclose(fp);
        close(fp);
    }
    else
        LOGD("---open file failed!");
    LOGD("----leave test------");
    
    return r;

    //return access("/sdcard/299792458.txt", 0);
}


JNIEXPORT jfloat JNICALL Java_com_gvitech_android_RenderControl_getStereoFusionDistance(JNIEnv* env, jobject obj)
{
    return sdk::RenderControl::GetStereoFusionDistance();
}
JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_setStereoFusionDistance(JNIEnv* env, jobject obj, jfloat newVal)
{
    sdk::RenderControl::SetStereoFusionDistance(newVal);
}

JNIEXPORT jfloat JNICALL Java_com_gvitech_android_RenderControl_getStereoEyeSeparation(JNIEnv* env, jobject obj)
{
    return sdk::RenderControl::GetStereoEyeSeparation();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_setStereoEyeSeparation(JNIEnv* env, jobject obj, jfloat newVal)
{
    sdk::RenderControl::SetStereoEyeSeparation(newVal);
}

JNIEXPORT jfloat JNICALL Java_com_gvitech_android_RenderControl_getStereoScreenDistance(JNIEnv* env, jobject obj)
{
    return sdk::RenderControl::GetStereoScreenDistance();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_setStereoScreenDistance(JNIEnv* env, jobject obj, jfloat newVal)
{
    sdk::RenderControl::SetStereoScreenDistance(newVal);
}


JNIEXPORT jfloat JNICALL Java_com_gvitech_android_RenderControl_getOculusInterpupillaryDistance(JNIEnv* env, jobject obj)
{
    return sdk::RenderControl::GetOculusInterpupillaryDistance();
}
JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_setOculusInterpupillaryDistance(JNIEnv* env, jobject obj, jfloat newVal)
{
    sdk::RenderControl::SetOculusInterpupillaryDistance(newVal);
}
JNIEXPORT jfloat JNICALL Java_com_gvitech_android_RenderControl_getOculusProjectionCenterOffset(JNIEnv* env, jobject obj)
{
    return sdk::RenderControl::GetOculusProjectionCenterOffset();
}
JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_setOculusProjectionCenterOffset(JNIEnv* env, jobject obj, jfloat newVal)
{
    sdk::RenderControl::SetOculusProjectionCenterOffset(newVal);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_RenderControl_setLicenseInfoT(JNIEnv* env, jobject obj,
                                                                              jstring licFile, jstring signature,
                                                                              jint year, jint month, jint day)
{
    std::string strLicFile = getSTLStringA(env, licFile);
    std::string strSignature = getSTLStringA(env, signature);
    LOGD("License file: %s, code: %s", strLicFile.c_str(), strSignature.c_str());
    return sdk::RenderControl::SetLicenseInfo(strLicFile, strSignature, year, month, day);
}



/**********************************************************
 * IViewport
 *********************************************************/
JNIEXPORT jboolean JNICALL Java_com_gvitech_android_IViewport_getLogoVisible(JNIEnv* env, jobject obj)
{
    return sdk::Viewport::GetLogoVisible();
}
JNIEXPORT void JNICALL Java_com_gvitech_android_IViewport_setLogoVisible(JNIEnv* env, jobject obj, jboolean newVal)
{
    sdk::Viewport::SetLogoVisible(newVal);
}
JNIEXPORT jboolean JNICALL Java_com_gvitech_android_IViewport_getCompassVisible(JNIEnv* env, jobject obj)
{
    return sdk::Viewport::GetCompassVisible();
}
JNIEXPORT void JNICALL Java_com_gvitech_android_IViewport_setCompassVisible(JNIEnv* env, jobject obj, jboolean newVal)
{
    sdk::Viewport::SetCompassVisible(newVal);
}
JNIEXPORT void JNICALL Java_com_gvitech_android_IViewport_setCompassOffset(JNIEnv* env, jobject obj, jfloat ox, jfloat oy)
{
    sdk::Viewport::SetCompassOffset(ox, oy);
}
JNIEXPORT void JNICALL Java_com_gvitech_android_IViewport_setThumbtackOffset(JNIEnv* env, jobject obj, jfloat ox, jfloat oy)
{
    sdk::Viewport::SetThumbtackOffset(ox, oy);
}
JNIEXPORT jint JNICALL Java_com_gvitech_android_IViewport_getViewportModeT(JNIEnv* env, jobject obj)
{
    return sdk::Viewport::GetViewportMode();
}
JNIEXPORT void JNICALL Java_com_gvitech_android_IViewport_setViewportMode(JNIEnv* env, jobject obj, jobject newVal)
{
    int nVal = getEnumValue(env, newVal);
    sdk::Viewport::SetViewportMode(nVal);
}


/**********************************************************
 * Camera
 *********************************************************/
JNIEXPORT jdouble JNICALL Java_com_gvitech_android_ICamera_getFlyTime(JNIEnv* env, jobject obj)
{
    return sdk::Camera::GetFlyTime();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_setFlyTime(JNIEnv* env, jobject obj, jdouble newVal)
{
    sdk::Camera::SetFlyTime(newVal);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_setCameraT(JNIEnv* env, jobject obj, jdouble x, jdouble y, jdouble z, jdouble heading, jdouble tilt, jdouble roll, jobject flags)
{
    int nFlags = getEnumValue(env, flags);
    LOGD("setCamera flags: %d", nFlags);
	sdk::Camera::SetCamera(x, y, z, heading, tilt, roll, nFlags);
}

JNIEXPORT jdoubleArray JNICALL Java_com_gvitech_android_ICamera_getCamera(JNIEnv* env, jobject obj)
{
	jdouble doubleBuf[6] = { 0 };
	sdk::Camera::GetCamera(doubleBuf[0], doubleBuf[1], doubleBuf[2], doubleBuf[3], doubleBuf[4], doubleBuf[5]);
    
	jdoubleArray doubleArray = env->NewDoubleArray(6);    
	env->SetDoubleArrayRegion(doubleArray, 0, 6, doubleBuf);
    
	return	doubleArray;
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_lookAtT(JNIEnv* env, jobject obj,
                                                                       jdouble x, jdouble y, jdouble z,
                                                                       jdouble distance,
                                                                       jdouble heading, jdouble tilt, jdouble roll
                                                                       )
{
    sdk::Camera::LookAt(x, y, z, distance, heading, tilt, roll);
}

JNIEXPORT jdoubleArray JNICALL Java_com_gvitech_android_ICamera_getAimingAnglesT(JNIEnv* env, jobject obj,
                                                                         jdouble x1, jdouble y1, jdouble z1,
                                                                         jdouble x2, jdouble y2, jdouble z2)
{
    jdouble doubleBuf[3] = { 0 };
    sdk::Camera::GetAimingAngles(x1, y1, z1, x2, y2, z2, doubleBuf[0], doubleBuf[1], doubleBuf[2]);
    
    jdoubleArray doubleArray = env->NewDoubleArray(3);
	env->SetDoubleArrayRegion(doubleArray, 0, 3, doubleBuf);
    
	return	doubleArray;
}



JNIEXPORT jint JNICALL Java_com_gvitech_android_ICamera_getFlyModeT(JNIEnv* env, jobject obj)
{
    return sdk::Camera::GetFlyMode();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_setFlyMode(JNIEnv* env, jobject obj, jobject newVal)
{
    int nMode = getEnumValue(env, newVal, true);
    sdk::Camera::SetFlyMode(nMode);
}


JNIEXPORT jfloat JNICALL Java_com_gvitech_android_ICamera_getWalkSpeed(JNIEnv* env, jobject obj)
{
    return sdk::Camera::GetWalkSpeed();
}


JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_setWalkSpeed(JNIEnv* env, jobject obj, jfloat newVal)
{
    sdk::Camera::SetWalkSpeed(newVal);
}


JNIEXPORT jint JNICALL Java_com_gvitech_android_ICamera_getCollisionDetectionModeT(JNIEnv* env, jobject obj)
{
    return sdk::Camera::GetCollisionDetectionMode();
}


JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_setCollisionDetectionMode(JNIEnv* env, jobject obj, jobject newVal)
{
    int nMode = getEnumValue(env, newVal);
    sdk::Camera::SetCollisionDetectionMode(nMode);
}


JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_flyToObject(JNIEnv* env, jobject obj, jint oid, jobject code)
{
    int nCode = getEnumValue(env, code, true);
    sdk::Camera::FlyToObject(oid, nCode);
}

JNIEXPORT jboolean JNICALL Java_com_gvitech_android_ICamera_screenToWorldT(JNIEnv* env, jobject obj,
                                                                      jint x, jint y, jobject ix,
                                                                      jobject iy, jobject iz,
                                                                      jobject iObject, jobject iFid
                                                                      )
{
    double ix_, iy_, iz_;
    int iObject_, iFid_;
    bool r = sdk::Camera::ScreenToWorld(x, y, ix_, iy_, iz_, iObject_, iFid_);
    
    jclass cls = env->FindClass("java/lang/Double");
    jfieldID fid = env->GetFieldID(cls, "value", "D");
    env->SetDoubleField(ix, fid, ix_);
    env->SetDoubleField(iy, fid, iy_);
    env->SetDoubleField(iz, fid, iz_);
    
    if(r)
    {
        cls = env->FindClass("java/lang/Integer");
        fid = env->GetFieldID(cls, "value", "I");
        env->SetIntField(iObject, fid, iObject_);
        env->SetIntField(iFid, fid, iFid_);
        
    }
    
    return r;
}

JNIEXPORT jboolean JNICALL Java_com_gvitech_android_ICamera_worldToScreenT(JNIEnv* env, jobject obj,
                                                                           jdouble worldX, jdouble worldY, jdouble worldZ,
                                                                           jobject screenX, jobject screenY,
                                                                           jint mode, jobject boolInScreen
                                                                           )
{
    double screenX_, screenY_;
    bool isInScreen_;
    bool r = sdk::Camera::WorldToScreen(worldX, worldY, worldZ, screenX_, screenY_, mode, isInScreen_);
    
    jclass cls = env->FindClass("java/lang/Double");
    jfieldID fid = env->GetFieldID(cls, "value", "D");
    env->SetDoubleField(screenX, fid, screenX_);
    env->SetDoubleField(screenY, fid, screenY_);

    cls = env->FindClass("java/lang/Boolean");
    fid = env->GetFieldID(cls, "value", "Z");
    env->SetBooleanField(boolInScreen, fid, isInScreen_);
    
    LOGD("worldToScreen screenX: %lf", screenX_);
    LOGD("worldToScreen screenY: %lf", screenY_);
    LOGD("worldToScreen boolInScreen: %d", isInScreen_);
    
    return r;
}


JNIEXPORT jfloat JNICALL Java_com_gvitech_android_ICamera_getNearClipPlane(JNIEnv* env, jobject obj)
{
    return sdk::Camera::GetNearClipPlane();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_setNearClipPlane(JNIEnv* env, jobject obj, jfloat newVal)
{
    sdk::Camera::SetNearClipPlane(newVal);
}

JNIEXPORT jfloat JNICALL Java_com_gvitech_android_ICamera_getFarClipPlane(JNIEnv* env, jobject obj)
{
    return sdk::Camera::GetFarClipPlane();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_setFarClipPlane(JNIEnv* env, jobject obj, jfloat newVal)
{
    sdk::Camera::SetFarClipPlane(newVal);

}

JNIEXPORT jboolean JNICALL Java_com_gvitech_android_ICamera_getAutoClipPlane(JNIEnv* env, jobject obj)
{
    return sdk::Camera::GetAutoClipPlane();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_setAutoClipPlane(JNIEnv* env, jobject obj, jboolean newVal)
{
    sdk::Camera::SetAutoClipPlane(newVal);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_stop(JNIEnv* env, jobject obj)
{
    sdk::Camera::Stop();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_enterOrthoModeT(JNIEnv* env, jobject obj, jdouble xmin, jdouble xmax, jdouble ymin, jdouble ymax, jdouble zmin, jdouble zmax, jdouble heading, jdouble tilt, jdouble roll)
{
    sdk::Camera::EnterOrthoMode(xmin, xmax, ymin, ymax, zmin, zmax, heading, tilt, roll);
}

JNIEXPORT jboolean JNICALL Java_com_gvitech_android_ICamera_isOrthoMode(JNIEnv* env, jobject obj)
{
    return sdk::Camera::IsOrthoMode();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_leaveOrthoMode(JNIEnv* env, jobject obj)
{
    sdk::Camera::LeaveOrthoMode();
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_ICamera_getWalkModeT(JNIEnv* env, jobject obj)
{
    return sdk::Camera::GetWalkMode();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_setWalkMode(JNIEnv* env, jobject obj, jobject newVal)
{
    int nMode = getEnumValue(env, newVal);
    sdk::Camera::SetWalkMode(nMode);
}

/**********************************************************
 * RObject
 *********************************************************/
JNIEXPORT jint JNICALL Java_com_gvitech_android_IRObject_getTypeT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::RObject::GetType(oid);
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_IRObject_getGuidT(JNIEnv* env, jobject obj, jint oid)
{
    std::string str = sdk::RObject::GetGuid(oid);
    return env->NewStringUTF(str.c_str());
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_IRObject_getClientDataT(JNIEnv* env, jobject obj, jint oid)
{
    std::wstring str = sdk::RObject::GetClientData(oid);
    return getJavaString(env, str);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IRObject_setClientDataT(JNIEnv* env, jobject obj, jint oid, jstring newVal)
{
    std::wstring str = getSTLString(env, newVal);
    sdk::RObject::SetClientData(oid, str);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_IRObject_getAttributeMaskT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::RObject::GetAttributeMask(oid);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IRObject_setAttributeMaskT(JNIEnv* env, jobject obj, jint oid, jobject newVal)
{
    int nMask = getEnumValue(env, newVal);
    sdk::RObject::SetAttributeMask(oid, nMask);
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_IRObject_getNameT(JNIEnv* env, jobject obj, jint oid)
{
    std::wstring str = sdk::RObject::GetName(oid);
    return getJavaString(env, str);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IRObject_setNameT(JNIEnv* env, jobject obj, jint oid, jstring newVal)
{
    std::wstring str = getSTLString(env, newVal);
    sdk::RObject::SetName(oid, str);
}

/**********************************************************
 * Label
 *********************************************************/
JNIEXPORT jstring JNICALL Java_com_gvitech_android_ILabel_getTextT(JNIEnv* env, jobject obj, jint oid)
{
    std::wstring str = sdk::Label::GetText(oid);
    return getJavaString(env, str);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ILabel_setTextT(JNIEnv* env, jobject obj, jint oid, jstring newVal)
{
    std::wstring str = getSTLString(env, newVal);
    sdk::Label::SetText(oid, str);
}

JNIEXPORT jdouble JNICALL Java_com_gvitech_android_ILabel_getXT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::Label::GetX(oid);
}

JNIEXPORT jdouble JNICALL Java_com_gvitech_android_ILabel_getYT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::Label::GetY(oid);
}

JNIEXPORT jdouble JNICALL Java_com_gvitech_android_ILabel_getZT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::Label::GetZ(oid);
}


/**********************************************************
 * RenderPoint
 *********************************************************/
JNIEXPORT jstring JNICALL Java_com_gvitech_android_IRenderPoint_getImageNameT(JNIEnv* env, jobject obj, jint oid)
{
    LOGD("Java_com_gvitech_android_IRenderPoint_getImageNameT");
    std::wstring str = sdk::RenderPoint::GetImageName(oid);
    return getJavaString(env, str);
}



/**********************************************************
 * ReferencePlane
 *********************************************************/
JNIEXPORT jdouble JNICALL Java_com_gvitech_android_IReferencePlane_getPlaneHeight(JNIEnv* env, jobject obj)
{
    return sdk::ReferencePlane::getPlaneHeight();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IReferencePlane_setPlaneHeight(JNIEnv* env, jobject obj, jdouble newVal)
{
    LOGD("-----Java_com_gvitech_android_IReferencePlane_setPlaneHeight");
    sdk::ReferencePlane::setPlaneHeight(newVal);
}




/**********************************************************
 * SkyBox
 *********************************************************/
JNIEXPORT void JNICALL Java_com_gvitech_android_ISkyBox_setImagePath(JNIEnv* env, jobject obj, jobject index, jstring imagePath)
{
    int idx = getEnumValue(env, index);
    std::wstring strPath = getSTLString(env, imagePath);
    
	sdk::SkyBox::SetImagePath(idx, strPath);
}
JNIEXPORT jint JNICALL Java_com_gvitech_android_ISkyBox_getWeatherT(JNIEnv* env, jobject obj)
{
	return	sdk::SkyBox::GetWeather();
}
JNIEXPORT void JNICALL Java_com_gvitech_android_ISkyBox_setWeather(JNIEnv* env, jobject obj, jobject weatherType)
{
    int wt = getEnumValue(env, weatherType);
    sdk::SkyBox::SetWeather(wt);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_ISkyBox_getFogModeT(JNIEnv* env, jobject obj)
{
	return	sdk::SkyBox::GetFogMode();
}
JNIEXPORT void JNICALL Java_com_gvitech_android_ISkyBox_setFogMode(JNIEnv* env, jobject obj, jobject fogMode)
{
    int mode = getEnumValue(env, fogMode, true);
    sdk::SkyBox::SetFogMode(mode);
}
JNIEXPORT jfloat JNICALL Java_com_gvitech_android_ISkyBox_getFogStartDistance(JNIEnv* env, jobject obj)
{
    return sdk::SkyBox::GetFogStartDistance();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ISkyBox_setFogStartDistance(JNIEnv* env, jobject obj, jfloat newVal)
{
    sdk::SkyBox::SetFogStartDistance(newVal);
}
JNIEXPORT jfloat JNICALL Java_com_gvitech_android_ISkyBox_getFogEndDistance(JNIEnv* env, jobject obj)
{
    return sdk::SkyBox::GetFogEndDistance();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ISkyBox_setFogEndDistance(JNIEnv* env, jobject obj, jfloat newVal)
{
    sdk::SkyBox::SetFogEndDistance(newVal);
}
JNIEXPORT jint JNICALL Java_com_gvitech_android_ISkyBox_getFogColor(JNIEnv* env, jobject obj)
{
    return sdk::SkyBox::GetFogColor();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ISkyBox_setFogColor(JNIEnv* env, jobject obj, jint color)
{
    sdk::SkyBox::SetFogColor(color);
}

/**********************************************************
 * Renderable
 *********************************************************/
JNIEXPORT jint JNICALL Java_com_gvitech_android_IRObject_getDepthTestModeT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::Renderable::GetDepthTestMode(oid);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderable_setDepthTestModeT(JNIEnv* env, jobject obj, jint oid, jobject newVal)
{
    int n = getEnumValue(env, newVal);
    sdk::Renderable::SetDepthTestMode(oid, n);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_IRenderable_getVisibleMaskT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::Renderable::GetVisibleMask(oid);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderable_setVisibleMaskT(JNIEnv* env, jobject obj, jint oid, jobject newVal)
{
    int nMask = getEnumValue(env, newVal);
    sdk::Renderable::SetVisibleMask(oid, nMask);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_IRObject_getMouseSelectMaskT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::Renderable::GetMouseSelectMask(oid);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderable_setMouseSelectMaskT(JNIEnv* env, jobject obj, jint oid, jobject newVal)
{
    int n = getEnumValue(env, newVal);
    sdk::Renderable::SetMouseSelectMask(oid, n);
}

JNIEXPORT jdouble JNICALL Java_com_gvitech_android_IRenderable_getMaxVisibleDistanceT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::Renderable::GetMaxVisibleDistance(oid);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderable_setMaxVisibleDistanceT(JNIEnv* env, jobject obj, jint oid, jdouble newVal)
{
    sdk::Renderable::SetMaxVisibleDistance(oid, newVal);
}
JNIEXPORT jdouble JNICALL Java_com_gvitech_android_IRenderable_getViewingDistanceT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::Renderable::GetViewingDistance(oid);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderable_setViewingDistanceT(JNIEnv* env, jobject obj, jint oid, jdouble newVal)
{
    sdk::Renderable::SetViewingDistance(oid, newVal);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderable_highlightT(JNIEnv* env, jobject obj, jint oid, jint color)
{
    sdk::Renderable::Highlight(oid, color);
}
JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderable_unhighlightT(JNIEnv* env, jobject obj, jint oid)
{
    sdk::Renderable::Unhighlight(oid);
}
JNIEXPORT jfloat JNICALL Java_com_gvitech_android_IRenderable_getMinVisiblePixelsT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::Renderable::GetMinVisiblePixels(oid);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderable_setMinVisiblePixelsT(JNIEnv* env, jobject obj, jint oid, jfloat newVal)
{
    sdk::Renderable::SetMinVisiblePixels(oid, newVal);
}

JNIEXPORT jdoubleArray JNICALL Java_com_gvitech_android_IRenderable_getEnvelopeT(JNIEnv* env, jobject obj, jint oid)
{
    jdouble doubleBuf[6] = { 0 };
    sdk::Renderable::GetEnvelope(oid, doubleBuf[0], doubleBuf[1], doubleBuf[2], doubleBuf[3], doubleBuf[4], doubleBuf[5]);
    
    jdoubleArray doubleArray = env->NewDoubleArray(6);
    env->SetDoubleArrayRegion(doubleArray, 0, 6, doubleBuf);
    
    return	doubleArray;
}

/**********************************************************
 * RenderGeometry
 *********************************************************/
JNIEXPORT jstring JNICALL Java_com_gvitech_android_IRenderGeometry_getFdeGeometryT(JNIEnv* env, jobject obj, jint oid)
{
    std::wstring str = sdk::RenderGeometry::GetFdeGeometry(oid);
    return getJavaString(env, str);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderGeometry_setFdeGeometryT(JNIEnv* env, jobject obj, jint oid, jstring wkt)
{
    std::wstring str = getSTLString(env, wkt);
    sdk::RenderGeometry::SetFdeGeometry(oid, str);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderGeometry_setXT(JNIEnv* env, jobject obj, jint oid, jdouble x)
{
    sdk::RenderGeometry::SetX(oid, x);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderGeometry_setYT(JNIEnv* env, jobject obj, jint oid, jdouble y)
{
    sdk::RenderGeometry::SetY(oid, y);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderGeometry_setZT(JNIEnv* env, jobject obj, jint oid, jdouble z)
{
    sdk::RenderGeometry::SetZ(oid, z);
}

/**********************************************************
 * FeatureLayer
 *********************************************************/
JNIEXPORT void JNICALL Java_com_gvitech_android_IFeatureLayer_highlightFeatureT(JNIEnv* env, jobject obj, jint layerId, jint featureId, jint color)
{
    sdk::FeatureLayer::HighlightFeature(layerId, featureId, color);
}
JNIEXPORT void JNICALL Java_com_gvitech_android_IFeatureLayer_unhighlightAll(JNIEnv* env, jobject obj)
{
    sdk::FeatureLayer::UnhighlightAll();
}
JNIEXPORT jlong JNICALL Java_com_gvitech_android_IFeatureLayer_searchByIdT(JNIEnv* env, jobject obj, jint layerId, jint featureId)
{
    int64_t rb = sdk::FeatureLayer::SearchById(layerId, featureId);
    LOGD("[FeatureLayer::searchByIdT] %d", rb);
    return rb;
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_IFeatureLayer_getFeatureClassIdT(JNIEnv* env, jobject obj, jint layerId)
{
    std::string str = sdk::FeatureLayer::GetFeatureClassId(layerId);
    return env->NewStringUTF(str.c_str());
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_IFeatureLayer_getGeometryFieldNameT(JNIEnv* env, jobject obj, jint layerId)
{
    std::wstring str = sdk::FeatureLayer::GetGeometryFieldName(layerId);
    return getJavaString(env, str);
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_IFeatureLayer_getDataSourceConnectionStringT(JNIEnv* env, jobject obj, jint layerId)
{
    std::wstring str = sdk::FeatureLayer::GetDataSourceConnectionString(layerId);
    return getJavaString(env, str);
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_IFeatureLayer_getDataSetNameT(JNIEnv* env, jobject obj, jint layerId)
{
    std::wstring str = sdk::FeatureLayer::GetDataSetName(layerId);
    return getJavaString(env, str);
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_IFeatureLayer_getFeatureClassNameT(JNIEnv* env, jobject obj, jint layerId)
{
    std::wstring str = sdk::FeatureLayer::GetFeatureClassName(layerId);
    return getJavaString(env, str);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IFeatureLayer_hiddenFeaturesT(JNIEnv* env, jobject obj, jint layerId, jintArray featureIds)
{
    std::vector<int> vec;
    jint* arr = env->GetIntArrayElements(featureIds, 0 );
    jsize len = env->GetArrayLength(featureIds);
    LOGD("打印hiddenFeaturesT");
    for(int i=0; i < len; i++)
    {
        vec.push_back(arr[i]);
        LOGD("%d: %d", i, arr[i]);
    }
    env->ReleaseIntArrayElements(featureIds, arr, 0);
    
    sdk::FeatureLayer::HiddenFeatures(layerId, vec);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_IFeatureLayer_createRenderModelPointT(JNIEnv* env, jobject obj,
                                                                                     jint layerId, jint featureId
                                                                                     )
{
    return sdk::FeatureLayer::CreateRenderModelPoint(layerId, featureId);
}

JNIEXPORT jlong JNICALL Java_com_gvitech_android_IFeatureLayer_searchT(JNIEnv* env, jobject obj, jint layerId, jlong filter)
{
    int64_t rbs = sdk::FeatureLayer::Search(layerId, filter);
    LOGD("[FeatureLayer::searchT] %lld", rbs);
    return rbs;
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IFeatureLayer_setGroupVisibleMaskT(JNIEnv* env, jobject obj, jint layerId, jint groupId, jobject newVal)
{
    int nVal = getEnumValue(env, newVal);
    sdk::FeatureLayer::SetGroupVisibleMask(layerId, groupId, nVal);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_IFeatureLayer_getGroupVisibleMaskT(JNIEnv* env, jobject obj, jint layerId, jint groupId)
{
    return sdk::FeatureLayer::GetGroupVisibleMask(layerId, groupId);
}

/**********************************************************
 * CameraTour
 *********************************************************/
JNIEXPORT jboolean JNICALL Java_com_gvitech_android_ICameraTour_fromXmlT(JNIEnv* env, jobject obj, jint oid, jstring xmlStr)
{
    std::wstring str = getSTLString(env, xmlStr);
    sdk::CameraTour::FromXml(oid, str);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ICameraTour_playT(JNIEnv* env, jobject obj, jint oid)
{
    sdk::CameraTour::Play(oid);

}

JNIEXPORT void JNICALL Java_com_gvitech_android_ICameraTour_pauseT(JNIEnv* env, jobject obj, jint oid)
{
    sdk::CameraTour::Pause(oid);
}


JNIEXPORT void JNICALL Java_com_gvitech_android_ICameraTour_stopT(JNIEnv* env, jobject obj, jint oid)
{
    sdk::CameraTour::Stop(oid);

}


JNIEXPORT jdouble JNICALL Java_com_gvitech_android_ICameraTour_getTotalTimeT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::CameraTour::GetTotalTime(oid);
}


JNIEXPORT jint JNICALL Java_com_gvitech_android_ICameraTour_getWaypointsNumberT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::CameraTour::GetWaypointsNumber(oid);

}


JNIEXPORT jdouble JNICALL Java_com_gvitech_android_ICameraTour_getTimeT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::CameraTour::GetTime(oid);

}


JNIEXPORT void JNICALL Java_com_gvitech_android_ICameraTour_setTimeT(JNIEnv* env, jobject obj, jint oid, jdouble newVal)
{
    sdk::CameraTour::SetTime(oid, newVal);
}


JNIEXPORT jint JNICALL Java_com_gvitech_android_ICameraTour_getIndexT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::CameraTour::GetIndex(oid);

}


JNIEXPORT void JNICALL Java_com_gvitech_android_ICameraTour_setIndexT(JNIEnv* env, jobject obj, jint oid, jint newVal)
{
    sdk::CameraTour::SetIndex(oid, newVal);
}


JNIEXPORT jboolean JNICALL Java_com_gvitech_android_ICameraTour_getAutoRepeatT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::CameraTour::GetAutoRepeat(oid);
}


JNIEXPORT void JNICALL Java_com_gvitech_android_ICameraTour_setAutoRepeatT(JNIEnv* env, jobject obj, jint oid, jboolean newVal)
{
    sdk::CameraTour::SetAutoRepeat(oid, newVal);
}



/**********************************************************
 * MotionPath
 *********************************************************/
JNIEXPORT jboolean JNICALL Java_com_gvitech_android_IMotionPath_fromXmlT(JNIEnv* env, jobject obj, jint oid, jstring xmlStr)
{
    std::wstring str = getSTLString(env, xmlStr);
    sdk::MotionPath::FromXml(oid, str);
}

JNIEXPORT jboolean JNICALL Java_com_gvitech_android_IMotionPath_playT(JNIEnv* env, jobject obj, jint oid)
{
    sdk::MotionPath::Play(oid);
    
}

JNIEXPORT jboolean JNICALL Java_com_gvitech_android_IMotionPath_pauseT(JNIEnv* env, jobject obj, jint oid)
{
    sdk::MotionPath::Pause(oid);
}


JNIEXPORT jboolean JNICALL Java_com_gvitech_android_IMotionPath_stopT(JNIEnv* env, jobject obj, jint oid)
{
    sdk::MotionPath::Stop(oid);
    
}


JNIEXPORT jdouble JNICALL Java_com_gvitech_android_IMotionPath_getTotalDurationT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::MotionPath::GetTotalDuration(oid);
}


JNIEXPORT jint JNICALL Java_com_gvitech_android_IMotionPath_getWaypointsNumberT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::MotionPath::GetWaypointsNumber(oid);
    
}


JNIEXPORT jdouble JNICALL Java_com_gvitech_android_IMotionPath_getTimeT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::MotionPath::GetTime(oid);
    
}


JNIEXPORT void JNICALL Java_com_gvitech_android_IMotionPath_setTimeT(JNIEnv* env, jobject obj, jint oid, jdouble newVal)
{
    sdk::MotionPath::SetTime(oid, newVal);
}


JNIEXPORT jint JNICALL Java_com_gvitech_android_IMotionPath_getIndexT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::MotionPath::GetIndex(oid);
    
}


JNIEXPORT void JNICALL Java_com_gvitech_android_IMotionPath_setIndexT(JNIEnv* env, jobject obj, jint oid, jint newVal)
{
    sdk::MotionPath::SetIndex(oid, newVal);
}


JNIEXPORT jboolean JNICALL Java_com_gvitech_android_IMotionPath_getAutoRepeatT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::MotionPath::GetAutoRepeat(oid);
}


JNIEXPORT void JNICALL Java_com_gvitech_android_IMotionPath_setAutoRepeatT(JNIEnv* env, jobject obj, jint oid, jboolean newVal)
{
    sdk::MotionPath::SetAutoRepeat(oid, newVal);
}



/**********************************************************
 * I3DTileLayer
 *********************************************************/
JNIEXPORT jboolean JNICALL Java_com_gvitech_android_I3DTileLayer_getGrayShowT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::ThreeDTileLayer::GetGrayShow(oid);
}
JNIEXPORT void JNICALL Java_com_gvitech_android_I3DTileLayer_setGrayShowT(JNIEnv* env, jobject obj, jint oid, jboolean newVal)
{
    sdk::ThreeDTileLayer::SetGrayShow(oid, newVal);
}
JNIEXPORT jfloat JNICALL Java_com_gvitech_android_I3DTileLayer_getGrayScalarT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::ThreeDTileLayer::GetGrayScalar(oid);

}
JNIEXPORT void JNICALL Java_com_gvitech_android_I3DTileLayer_setGrayScalarT(JNIEnv* env, jobject obj, jint oid, jfloat newVal)
{
    sdk::ThreeDTileLayer::SetGrayScalar(oid, newVal);

}


/**********************************************************
 * ObjectManager
 *********************************************************/
JNIEXPORT jintArray Java_com_gvitech_android_IObjectManager_createFeatureLayerT(JNIEnv* env, jobject obj, jstring sdbPath, jlong geometryRender)
{
    jintArray rets = NULL;
    
    std::wstring str = getSTLString(env, sdbPath);
    std::vector<int> ids = sdk::ObjectManager::CreateFeatureLayer(str, geometryRender);
    jsize len = ids.size();
    if(len > 0)
    {
        rets = env->NewIntArray(len);
        env->SetIntArrayRegion(rets, 0, len, &ids[0]);
    }
    
    return rets;
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_IObjectManager_createCameraTourT(JNIEnv* env, jobject obj)
{
	return	sdk::ObjectManager::CreateCameraTour();
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_IObjectManager_createMotionPathT(JNIEnv* env, jobject obj)
{
	return	sdk::ObjectManager::CreateMotionPath();
}

JNIEXPORT jboolean JNICALL Java_com_gvitech_android_IObjectManager_deleteObject(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::ObjectManager::DeleteObject(oid);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_IObjectManager_createLabelT(JNIEnv* env, jobject obj,
                                                                                 jdouble x, jdouble y, jdouble z,
                                                                                 jstring text,
                                                                                 jint textColor,
                                                                                 jint textSize,
                                                                                 jint bgColor,
                                                                                 jdouble verticalOffset)
{
    std::wstring strText = getSTLString(env, text);
    return sdk::ObjectManager::CreateLabel(x, y, z, strText, textColor, textSize, bgColor, verticalOffset);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_IObjectManager_create3DTileLayerT(JNIEnv* env, jobject obj, jstring layerInfo, jstring password)
{
    std::wstring strLayerInfo = getSTLString(env, layerInfo);
    std::wstring strPass = getSTLString(env, password);
    return sdk::ObjectManager::Create3DTileLayer(strLayerInfo, strPass);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_IObjectManager_createRenderPolylineT(JNIEnv* env, jobject obj,
                                                                                    jdoubleArray arrPoints,
                                                                                    jint lineColor,
                                                                                    jfloat lineWidth
                                                                                    )
{
    std::vector<double> vec;
    jdouble* arr = (env)->GetDoubleArrayElements(arrPoints, 0 );
    jsize len = env->GetArrayLength(arrPoints);
//    LOGD("打印createRenderPolylineT");
    for(int i=0; i < len; i++)
    {
        vec.push_back(arr[i]);
//        LOGD("%d: %f", i, arr[i]);
    }
    env->ReleaseDoubleArrayElements(arrPoints, arr, 0);
    
    return sdk::ObjectManager::CreateRenderPolyline(vec, lineColor, lineWidth);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_IObjectManager_createRenderPolygonT(JNIEnv* env, jobject obj,
                                                                                     jdoubleArray arrPoints,
                                                                                     jint fillColor
                                                                                     )
{
    std::vector<double> vec;
    jdouble* arr = (env)->GetDoubleArrayElements(arrPoints, 0 );
    jsize len = env->GetArrayLength(arrPoints);
    for(int i=0; i < len; i++)
    {
        vec.push_back(arr[i]);
    }
    env->ReleaseDoubleArrayElements(arrPoints, arr, 0);
    
    return sdk::ObjectManager::CreateRenderPolygon(vec, fillColor);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_IObjectManager_createRenderPointT(JNIEnv* env, jobject obj,
                                                                                  jdouble x, jdouble y, jdouble z,
                                                                                  jstring imagePath,
                                                                                  jint size)
{
    std::wstring strImagePath = getSTLString(env, imagePath);
    return sdk::ObjectManager::CreateRenderPoint(x, y, z, strImagePath, size);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_IObjectManager_getObjectByIdT(JNIEnv* env, jobject obj, jint oid)
{
    return sdk::ObjectManager::GetObjectById(oid);
}



/**********************************************************
 * CacheManager
 *********************************************************/
JNIEXPORT jboolean JNICALL Java_com_gvitech_android_ICacheManager_getFileCacheEnabled(JNIEnv* env, jobject obj)
{
    return sdk::CacheManager::GetFileCacheEnabled();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ICacheManager_setFileCacheEnabled(JNIEnv* env, jobject obj, jboolean newVal)
{
    sdk::CacheManager::SetFileCacheEnabled(newVal);
}


JNIEXPORT jstring JNICALL Java_com_gvitech_android_ICacheManager_getFileCachePath(JNIEnv* env, jobject obj)
{
    std::wstring str = sdk::CacheManager::GetFileCachePath();
    return getJavaString(env, str);
}


JNIEXPORT void JNICALL Java_com_gvitech_android_ICacheManager_setFileCachePath(JNIEnv* env, jobject obj, jstring newVal)
{
    std::wstring str = getSTLString(env, newVal);
    sdk::CacheManager::SetFileCachePath(str.c_str());
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_ICacheManager_getTileCacheFileName(JNIEnv* env, jobject obj, jstring newVal)
{
    std::wstring str = getSTLString(env, newVal);
    std::wstring cacheFileName = sdk::CacheManager::GetTileCacheFileName(str.c_str());
    return getJavaString(env, cacheFileName);
}


/**********************************************************
 * ExportManager
 *********************************************************/
JNIEXPORT jintArray JNICALL Java_com_gvitech_android_IExportManager_exportImageT(JNIEnv* env, jobject obj, jstring newVal)
{
    std::string strFilePath = getSTLStringA(env, newVal);
    std::vector<int> vRet = sdk::ExportManager::ExportImage(strFilePath);
    
    jintArray r = env->NewIntArray(3);
    env->SetIntArrayRegion(r, 0, 3, &vRet[0]);
    return r;
}

/**********************************************************
 * GeometryRender
 *********************************************************/
JNIEXPORT jint JNICALL Java_com_gvitech_android_IGeometryRender_getRenderTypeT(JNIEnv* env, jobject obj, jlong geometryRender)
{
    LOGD("[GeometryRender::getRenderType] %d", geometryRender);
    return sdk::GeometryRender::GetRenderType(geometryRender);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IGeometryRender_setRenderGroupFieldT(JNIEnv* env, jobject obj, jlong geometryRender, jstring newVal)
{
    std::wstring fieldName = getSTLString(env, newVal);
    LOGD("[GeometryRender::setRenderGroupField] %d", geometryRender);
    sdk::GeometryRender::SetRenderGroupField(geometryRender, fieldName);
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_IGeometryRender_getRenderGroupFieldT(JNIEnv* env, jobject obj, jlong geometryRender)
{
    LOGD("[GeometryRender::getRenderGroupField] %d", geometryRender);
    std::wstring fieldName = sdk::GeometryRender::GetRenderGroupField(geometryRender);
    return getJavaString(env, fieldName);
}

/**********************************************************
 * SimpleGeometryRender
 *********************************************************/
JNIEXPORT jlong JNICALL Java_com_gvitech_android_ISimpleGeometryRender_createT(JNIEnv* env, jobject obj)
{
    return sdk::SimpleGeometryRender::Create();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ISimpleGeometryRender_setSymbolT(JNIEnv* env, jobject obj, jlong geometryRender, jlong symbol)
{
    sdk::SimpleGeometryRender::SetSymbol(geometryRender, symbol);
}

/**********************************************************
 * GeometrySymbol
 *********************************************************/
JNIEXPORT jint JNICALL Java_com_gvitech_android_IGeometrySymbol_getSymbolTypeT(JNIEnv* env, jobject obj, jlong geometrySymbol)
{
    LOGD("[GeometrySymbol::getSymbolType] %d", geometrySymbol);
    return sdk::GeometrySymbol::GetSymbolType(geometrySymbol);
}

/**********************************************************
 * CurveSymbol
 *********************************************************/
JNIEXPORT jlong JNICALL Java_com_gvitech_android_ICurveSymbol_createT(JNIEnv* env, jobject obj)
{
    return sdk::CurveSymbol::Create();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ICurveSymbol_setColorT(JNIEnv* env, jobject obj, jlong geometrySymbol, jint color)
{
    sdk::CurveSymbol::SetColor(geometrySymbol, color);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ICurveSymbol_setWidthT(JNIEnv* env, jobject obj, jlong geometrySymbol, jfloat width)
{
    sdk::CurveSymbol::SetWidth(geometrySymbol, width);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ICurveSymbol_setRepeatLengthT(JNIEnv* env, jobject obj, jlong geometrySymbol, jfloat repeatLength)
{
    sdk::CurveSymbol::SetRepeatLength(geometrySymbol, repeatLength);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ICurveSymbol_setImageNameT(JNIEnv* env, jobject obj, jlong geometrySymbol, jstring imageName)
{
    std::wstring strPath = getSTLString(env, imageName);
    sdk::CurveSymbol::SetImageName(geometrySymbol, strPath);
}

/**********************************************************
 * SurfaceSymbol
 *********************************************************/
JNIEXPORT jlong JNICALL Java_com_gvitech_android_ISurfaceSymbol_createT(JNIEnv* env, jobject obj)
{
    return sdk::SurfaceSymbol::Create();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ISurfaceSymbol_setColorT(JNIEnv* env, jobject obj, jlong geometrySymbol, jint color)
{
    sdk::SurfaceSymbol::SetColor(geometrySymbol, color);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ISurfaceSymbol_setBoundarySymbolT(JNIEnv* env, jobject obj, jlong geometrySymbol, jlong symbol)
{
    sdk::SurfaceSymbol::SetBoundarySymbol(geometrySymbol, symbol);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ISurfaceSymbol_setImageNameT(JNIEnv* env, jobject obj, jlong geometrySymbol, jstring imageName)
{
    std::wstring strPath = getSTLString(env, imageName);
    sdk::SurfaceSymbol::SetImageName(geometrySymbol, strPath);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ISurfaceSymbol_setRepeatLengthUT(JNIEnv* env, jobject obj, jlong geometrySymbol, jfloat repeatLength)
{
    sdk::SurfaceSymbol::SetRepeatLengthU(geometrySymbol, repeatLength);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ISurfaceSymbol_setRepeatLengthVT(JNIEnv* env, jobject obj, jlong geometrySymbol, jfloat repeatLength)
{
    sdk::SurfaceSymbol::SetRepeatLengthV(geometrySymbol, repeatLength);
}

/**********************************************************
 * PointSymbol
 *********************************************************/
JNIEXPORT void JNICALL Java_com_gvitech_android_IPointSymbol_setColorT(JNIEnv* env, jobject obj, jlong geometrySymbol, jint color)
{
    sdk::PointSymbol::SetColor(geometrySymbol, color);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IPointSymbol_setSizeT(JNIEnv* env, jobject obj, jlong geometrySymbol, jint size)
{
    sdk::PointSymbol::SetSize(geometrySymbol, size);
}

/**********************************************************
 * SimplePointSymbol
 *********************************************************/
JNIEXPORT jlong JNICALL Java_com_gvitech_android_ISimplePointSymbol_createT(JNIEnv* env, jobject obj)
{
    return sdk::SimplePointSymbol::Create();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ISimplePointSymbol_setFillColorT(JNIEnv* env, jobject obj, jlong geometrySymbol, jint color)
{
    sdk::SimplePointSymbol::SetFillColor(geometrySymbol, color);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_ISimplePointSymbol_setOutlineColorT(JNIEnv* env, jobject obj, jlong geometrySymbol, jint color)
{
    sdk::SimplePointSymbol::SetOutlineColor(geometrySymbol, color);
}

/**********************************************************
 * ImagePointSymbol
 *********************************************************/
JNIEXPORT jlong JNICALL Java_com_gvitech_android_IImagePointSymbol_createT(JNIEnv* env, jobject obj)
{
    return sdk::ImagePointSymbol::Create();
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IImagePointSymbol_setImageNameT(JNIEnv* env, jobject obj, jlong geometrySymbol, jstring imageName)
{
    std::wstring strPath = getSTLString(env, imageName);
    sdk::ImagePointSymbol::SetImageName(geometrySymbol, strPath);
}

/**********************************************************
 * ConnectionInfo
 *********************************************************/
JNIEXPORT jlong JNICALL Java_com_gvitech_android_IConnectionInfo_createT(JNIEnv* env, jobject obj)
{
    return sdk::ConnectionInfo::Create();
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_IConnectionInfo_getTypeT(JNIEnv* env, jobject obj, jlong connInfo)
{
    LOGD("[ConnectionInfo::getType] %d", connInfo);
    return sdk::ConnectionInfo::GetType(connInfo);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IConnectionInfo_setTypeT(JNIEnv* env, jobject obj, jlong connInfo, jobject newVal)
{
    int nVal = getEnumValue(env, newVal);
    LOGD("[ConnectionInfo::setType] %d", nVal);
    sdk::ConnectionInfo::SetType(connInfo, nVal);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IConnectionInfo_freePtrObject(JNIEnv* env, jobject obj, jlong connInfo)
{
    sdk::ConnectionInfo::FreePtrObject(connInfo);
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_IConnectionInfo_getDataBaseNameT(JNIEnv* env, jobject obj, jlong connInfo)
{
    std::wstring str = sdk::ConnectionInfo::GetDataBaseName(connInfo);
    LOGD("[ConnectionInfo::GetDataBaseName] %s", gfx::convertUTF16toUTF8(str).c_str());
    return getJavaString(env, str);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IConnectionInfo_setDataBaseNameT(JNIEnv* env, jobject obj, jlong connInfo, jstring newVal)
{
    std::wstring str = getSTLString(env, newVal);
    sdk::ConnectionInfo::SetDataBaseName(connInfo, str.c_str());
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IConnectionInfo_setServerT(JNIEnv* env, jobject obj, jlong connInfo, jstring newVal)
{
    std::wstring str = getSTLString(env, newVal);
    sdk::ConnectionInfo::SetServer(connInfo, str.c_str());
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IConnectionInfo_setPortT(JNIEnv* env, jobject obj, jlong connInfo, jint newVal)
{
    sdk::ConnectionInfo::SetPort(connInfo, newVal);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IConnectionInfo_setUserT(JNIEnv* env, jobject obj, jlong connInfo, jstring newVal)
{
    std::wstring str = getSTLString(env, newVal);
    sdk::ConnectionInfo::SetUser(connInfo, str.c_str());
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IConnectionInfo_setPwdT(JNIEnv* env, jobject obj, jlong connInfo, jstring newVal)
{
    std::wstring str = getSTLString(env, newVal);
    sdk::ConnectionInfo::SetPwd(connInfo, str.c_str());
}

/**********************************************************
 * DataSourceFactory
 *********************************************************/
JNIEXPORT jboolean JNICALL Java_com_gvitech_android_IDataSourceFactory_hasDataSourceT(JNIEnv* env, jobject obj, jlong connInfo)
{
    return sdk::DataSourceFactory::HasDataSource(connInfo);
}

JNIEXPORT jobjectArray JNICALL Java_com_gvitech_android_IDataSourceFactory_getDataBaseNamesT(JNIEnv* env, jobject obj,
                                                                                     jlong connInfo, jboolean bOnlyFdb,
                                                                                     jobject iDbCounts)
{
    wchar_t** pDbNames = NULL;
    int dbCounts = 0;
    sdk::DataSourceFactory::GetDataBaseNames(connInfo, bOnlyFdb, &pDbNames, &dbCounts);
    LOGD("[DataSourceFactory::getDataBaseNames] %d", dbCounts);
//    LOGD("[DataSourceFactory::getDataBaseNames] %s", gfx::convertUTF16toUTF8(pDbNames[0]).c_str());
    
    jclass cls = env->FindClass("java/lang/String");
    jobjectArray texts = env->NewObjectArray((jsize)dbCounts, cls, 0);
    int i=0;
    jstring jstr;
    for (; i<dbCounts; i++) {
        jstr = getJavaString(env, pDbNames[i]);
        env->SetObjectArrayElement(texts, i, jstr);
    }
    
    cls = env->FindClass("java/lang/Integer");
    jfieldID fid = env->GetFieldID(cls, "value", "I");
    env->SetIntField(iDbCounts, fid, dbCounts);
    
    return texts;
}

JNIEXPORT jlong JNICALL Java_com_gvitech_android_IDataSourceFactory_openDataSourceT(JNIEnv* env, jobject obj, jlong connInfo)
{
    return sdk::DataSourceFactory::OpenDataSource(connInfo);
}

/**********************************************************
 * DataSource
 *********************************************************/
JNIEXPORT jobjectArray JNICALL Java_com_gvitech_android_IDataSource_getFeatureDatasetNamesT(JNIEnv* env, jobject obj, jlong dataSource, jobject iDsetCounts)
{
    wchar_t** pDsetNames = NULL;
    int dsetCounts = 0;
    sdk::DataSource::GetFeatureDatasetNames(dataSource, &pDsetNames, &dsetCounts);
    LOGD("[DataSource::GetFeatureDatasetNames] %d", dsetCounts);
    
    jclass cls = env->FindClass("java/lang/String");
    jobjectArray texts = env->NewObjectArray((jsize)dsetCounts, cls, 0);
    int i=0;
    jstring jstr;
    for (; i<dsetCounts; i++) {
        jstr = getJavaString(env, pDsetNames[i]);
        env->SetObjectArrayElement(texts, i, jstr);
    }
    
    cls = env->FindClass("java/lang/Integer");
    jfieldID fid = env->GetFieldID(cls, "value", "I");
    env->SetIntField(iDsetCounts, fid, dsetCounts);
    
    return texts;
}

JNIEXPORT jlong JNICALL Java_com_gvitech_android_IDataSource_openFeatureDatasetT(JNIEnv* env, jobject obj, jlong dataSource, jstring newVal)
{
    std::wstring str = getSTLString(env, newVal);
    return sdk::DataSource::OpenFeatureDataset(dataSource, str.c_str());
}

/**********************************************************
 * FeatureDataset
 *********************************************************/
JNIEXPORT jobjectArray JNICALL Java_com_gvitech_android_IFeatureDataset_getNamesByTypeT(JNIEnv* env, jobject obj, jlong dataset, jobject iTableCounts, jobject typeVal)
{
    int tVal = getEnumValue(env, typeVal, true);
    
    wchar_t** pTableNames = NULL;
    int dtableCounts = 0;
    sdk::FeatureDataset::GetNamesByType(dataset, &pTableNames, &dtableCounts, tVal);
    LOGD("[FeatureDataset::GetNamesByType] %d", dtableCounts);
    
    jclass cls = env->FindClass("java/lang/String");
    jobjectArray texts = env->NewObjectArray((jsize)dtableCounts, cls, 0);
    int i=0;
    jstring jstr;
    for (; i<dtableCounts; i++) {
        jstr = getJavaString(env, pTableNames[i]);
        env->SetObjectArrayElement(texts, i, jstr);
    }
    
    cls = env->FindClass("java/lang/Integer");
    jfieldID fid = env->GetFieldID(cls, "value", "I");
    env->SetIntField(iTableCounts, fid, dtableCounts);
    
    return texts;
}

JNIEXPORT jlong JNICALL Java_com_gvitech_android_IFeatureDataset_openFeatureClassT(JNIEnv* env, jobject obj, jlong dataset, jstring newVal)
{
    std::wstring str = getSTLString(env, newVal);
    return sdk::FeatureDataset::OpenFeatureClass(dataset, str.c_str());
}

/**********************************************************
 * FeatureClass
 *********************************************************/
JNIEXPORT jlong JNICALL Java_com_gvitech_android_IFeatureClass_searchT(JNIEnv* env, jobject obj, jlong featureclass, jlong filter, jboolean bReuseRow)
{
    return sdk::FeatureClass::Search(featureclass, filter, bReuseRow);
}

/**********************************************************
 * QueryFilter
 *********************************************************/
JNIEXPORT jlong JNICALL Java_com_gvitech_android_IQueryFilter_createT(JNIEnv* env, jobject obj)
{
    return sdk::QueryFilter::Create();
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_IQueryFilter_getWhereClauseT(JNIEnv* env, jobject obj, jlong queryFilter)
{
    std::wstring str = sdk::QueryFilter::GetWhereClause(queryFilter);
    return getJavaString(env, str);
}

JNIEXPORT void JNICALL Java_com_gvitech_android_IQueryFilter_setWhereClauseT(JNIEnv* env, jobject obj, jlong queryFilter, jstring newVal)
{
    std::wstring str = getSTLString(env, newVal);
    sdk::QueryFilter::SetWhereClause(queryFilter, str.c_str());
}

/**********************************************************
 * FdeCursor
 *********************************************************/
JNIEXPORT jlong JNICALL Java_com_gvitech_android_IFdeCursor_nextRowT(JNIEnv* env, jobject obj, jlong fdecursor)
{
    int64_t rb = sdk::FdeCursor::NextRow(fdecursor);
    LOGD("[FdeCursor::nextRowT] %lld", rb);
    return rb;
}

/**********************************************************
 * RowBufferCollection
 *********************************************************/
JNIEXPORT jint JNICALL Java_com_gvitech_android_IRowBufferCollection_getCountT(JNIEnv* env, jobject obj, jlong buffercollection)
{
    return sdk::RowBufferCollection::GetCount(buffercollection);
}

JNIEXPORT jlong JNICALL Java_com_gvitech_android_IRowBufferCollection_getRowBufferT(JNIEnv* env, jobject obj, jlong buffercollection, jint index)
{
    int64_t rb = sdk::RowBufferCollection::Get(buffercollection, index);
    LOGD("[RowBufferCollection::getRowBufferT] %lld", rb);
    return rb;
}


/**********************************************************
 * RowBuffer
 *********************************************************/
JNIEXPORT jint JNICALL Java_com_gvitech_android_IRowBuffer_getFieldCountT(JNIEnv* env, jobject obj, jlong rowbuffer)
{
    return sdk::RowBuffer::GetFieldCount(rowbuffer);
}

JNIEXPORT jlong JNICALL Java_com_gvitech_android_IRowBuffer_getFieldsT(JNIEnv* env, jobject obj, jlong rowbuffer)
{
    return sdk::RowBuffer::GetFields(rowbuffer);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_IRowBuffer_fieldIndexT(JNIEnv* env, jobject obj, jlong rowbuffer, jstring newVal)
{
    std::wstring str = getSTLString(env, newVal);
    return sdk::RowBuffer::FieldIndex(rowbuffer, str.c_str());
}

JNIEXPORT jbyte JNICALL Java_com_gvitech_android_IRowBuffer_getInt8T(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos)
{
    return sdk::RowBuffer::GetInt8(rowbuffer, pos);
}

JNIEXPORT jshort JNICALL Java_com_gvitech_android_IRowBuffer_getInt16T(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos)
{
    return sdk::RowBuffer::GetInt16(rowbuffer, pos);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_IRowBuffer_getInt32T(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos)
{
    return sdk::RowBuffer::GetInt32(rowbuffer, pos);
}

JNIEXPORT jlong JNICALL Java_com_gvitech_android_IRowBuffer_getInt64T(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos)
{
    return sdk::RowBuffer::GetInt64(rowbuffer, pos);
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_IRowBuffer_getStringT(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos)
{
    std::wstring str = sdk::RowBuffer::GetString(rowbuffer, pos);
    return getJavaString(env, str);
}

JNIEXPORT jfloat JNICALL Java_com_gvitech_android_IRowBuffer_getFloatT(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos)
{
    return sdk::RowBuffer::GetFloat(rowbuffer, pos);
}

JNIEXPORT jdouble JNICALL Java_com_gvitech_android_IRowBuffer_getDoubleT(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos)
{
    return sdk::RowBuffer::GetDouble(rowbuffer, pos);
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_IRowBuffer_getDateTimeT(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos)
{
    std::wstring str = sdk::RowBuffer::GetDateTime(rowbuffer, pos);
    return getJavaString(env, str);
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_IRowBuffer_getBlobT(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos)
{
    std::wstring str = sdk::RowBuffer::GetBlob(rowbuffer, pos);
    return getJavaString(env, str);
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_IRowBuffer_getGeometryT(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos)
{
    std::wstring str = sdk::RowBuffer::GetGeometry(rowbuffer, pos);
    return getJavaString(env, str);
}

/**********************************************************
 * FieldInfoCollection
 *********************************************************/
JNIEXPORT jint JNICALL Java_com_gvitech_android_IFieldInfoCollection_getCountT(JNIEnv* env, jobject obj, jlong fieldcollection)
{
    return sdk::FieldInfoCollection::GetCount(fieldcollection);
}

JNIEXPORT jlong JNICALL Java_com_gvitech_android_IFieldInfoCollection_getFieldInfoT(JNIEnv* env, jobject obj, jlong fieldcollection, jint index)
{
    return sdk::FieldInfoCollection::Get(fieldcollection, index);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_IFieldInfoCollection_indexOfT(JNIEnv* env, jobject obj, jlong fieldcollection, jstring newVal)
{
    std::wstring str = getSTLString(env, newVal);
    return sdk::FieldInfoCollection::IndexOf(fieldcollection, str.c_str());
}

/**********************************************************
 * FieldInfo
 *********************************************************/
JNIEXPORT jint JNICALL Java_com_gvitech_android_IFieldInfo_getFieldTypeT(JNIEnv* env, jobject obj, jlong fieldinfo)
{
    LOGD("[FieldInfo::getType] %d", fieldinfo);
    return sdk::FieldInfo::GetFieldType(fieldinfo);
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_IFieldInfo_getNameT(JNIEnv* env, jobject obj, jlong fieldinfo)
{
    std::wstring str = sdk::FieldInfo::GetName(fieldinfo);
    return getJavaString(env, str);
}

JNIEXPORT jlong JNICALL Java_com_gvitech_android_IFieldInfo_getDomainT(JNIEnv* env, jobject obj, jlong fieldinfo)
{
    return sdk::FieldInfo::GetDomain(fieldinfo);
}

/**********************************************************
 * Domain
 *********************************************************/
JNIEXPORT jint JNICALL Java_com_gvitech_android_IDomain_getFieldTypeT(JNIEnv* env, jobject obj, jlong domian)
{
    return sdk::Domain::GetFieldType(domian);
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_IDomain_getNameT(JNIEnv* env, jobject obj, jlong domain)
{
    std::wstring str = sdk::Domain::GetName(domain);
    return getJavaString(env, str);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_IDomain_getDomainTypeT(JNIEnv* env, jobject obj, jlong domain)
{
    return sdk::Domain::GetDomainType(domain);
}

/**********************************************************
 * CodedValueDomain
 *********************************************************/
JNIEXPORT jint JNICALL Java_com_gvitech_android_ICodedValueDomain_getCodeCountT(JNIEnv* env, jobject obj, jlong codedValueDomain)
{
    return sdk::CodedValueDomain::GetCodeCount(codedValueDomain);
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_ICodedValueDomain_getCodeNameT(JNIEnv* env, jobject obj, jlong codedValueDomain, jint pos)
{
    std::wstring str = sdk::CodedValueDomain::GetCodeName(codedValueDomain, pos);
    return getJavaString(env, str);
}

JNIEXPORT jbyte JNICALL Java_com_gvitech_android_ICodedValueDomain_getInt8T(JNIEnv* env, jobject obj, jlong codedValueDomain, jint pos)
{
    return sdk::CodedValueDomain::GetInt8(codedValueDomain, pos);
}

JNIEXPORT jshort JNICALL Java_com_gvitech_android_ICodedValueDomain_getInt16T(JNIEnv* env, jobject obj, jlong codedValueDomain, jint pos)
{
    return sdk::CodedValueDomain::GetInt16(codedValueDomain, pos);
}

JNIEXPORT jint JNICALL Java_com_gvitech_android_ICodedValueDomain_getInt32T(JNIEnv* env, jobject obj, jlong codedValueDomain, jint pos)
{
    return sdk::CodedValueDomain::GetInt32(codedValueDomain, pos);
}

JNIEXPORT jlong JNICALL Java_com_gvitech_android_ICodedValueDomain_getInt64T(JNIEnv* env, jobject obj, jlong codedValueDomain, jint pos)
{
    return sdk::CodedValueDomain::GetInt64(codedValueDomain, pos);
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_ICodedValueDomain_getStringT(JNIEnv* env, jobject obj, jlong codedValueDomain, jint pos)
{
    std::wstring str = sdk::CodedValueDomain::GetString(codedValueDomain, pos);
    return getJavaString(env, str);
}

JNIEXPORT jfloat JNICALL Java_com_gvitech_android_ICodedValueDomain_getFloatT(JNIEnv* env, jobject obj, jlong codedValueDomain, jint pos)
{
    return sdk::CodedValueDomain::GetFloat(codedValueDomain, pos);
}

JNIEXPORT jdouble JNICALL Java_com_gvitech_android_ICodedValueDomain_getDoubleT(JNIEnv* env, jobject obj, jlong codedValueDomain, jint pos)
{
    return sdk::CodedValueDomain::GetDouble(codedValueDomain, pos);
}

JNIEXPORT jstring JNICALL Java_com_gvitech_android_ICodedValueDomain_getDateTimeT(JNIEnv* env, jobject obj, jlong codedValueDomain, jint pos)
{
    std::wstring str = sdk::CodedValueDomain::GetDateTime(codedValueDomain, pos);
    return getJavaString(env, str);
}

/**********************************************************
 * TestNative
 *********************************************************/
JNIEXPORT jint Java_com_gvitech_android_TestNative_add(JNIEnv* env, jobject obj, jint x, jint y)
{
    return x+y;
}


