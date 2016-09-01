#ifndef JNIAPI_H
#define JNIAPI_H

#include <jni.h>

extern "C" {
    
    /**********************************************************
     * RenderControl
     *********************************************************/
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_RenderControl_initializeT(JNIEnv* env, jobject obj, jobject surface, jobject assetManager);
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_RenderControl_initialize2T(JNIEnv* env, jobject obj, jobject surface, jobject assetManager, jstring wkt);
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_RenderControl_uninitialize(JNIEnv* env, jobject obj);
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_RenderControl_resetView(JNIEnv* env, jobject obj, jobject surface);    
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_RenderControl_pauseRendering(JNIEnv* env, jobject obj, jboolean dumpMemory);
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_RenderControl_resumeRendering(JNIEnv* env, jobject obj);
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_RenderControl_reset(JNIEnv* env, jobject obj, jobject surface, jboolean isPlanarTerrain);        
	JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_nativeTouchEvent(JNIEnv* env, jobject obj, jint touchCount, jintArray touchPosArray);
	JNIEXPORT jboolean JNICALL Java_com_gvitech_android_RenderControl_enterWalkMode(JNIEnv* env, jobject obj, jstring roadSdb, jstring password);
    
    JNIEXPORT jint JNICALL Java_com_gvitech_android_RenderControl_getInteractModeT(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_setInteractMode(JNIEnv* env, jobject obj, jobject newVal);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_RenderControl_getMouseSelectModeT(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_setMouseSelectMode(JNIEnv* env, jobject obj, jobject newVal);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_RenderControl_getMouseSelectObjectMask(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_setMouseSelectObjectMask(JNIEnv* env, jobject obj, jint newVal);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_RenderControl_getMeasurementModeT(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_setMeasurementMode(JNIEnv* env, jobject obj, jobject newVal);
    JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_sensorChanged(JNIEnv* env, jobject obj, jdouble roll, jdouble pitch, jdouble yaw);
    JNIEXPORT jdouble JNICALL Java_com_gvitech_android_RenderControl_internalTest(JNIEnv* env, jobject obj, jstring p1, jstring p2);

    JNIEXPORT jfloat JNICALL Java_com_gvitech_android_RenderControl_getStereoFusionDistance(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_setStereoFusionDistance(JNIEnv* env, jobject obj, jfloat newVal);
    JNIEXPORT jfloat JNICALL Java_com_gvitech_android_RenderControl_getStereoEyeSeparation(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_setStereoEyeSeparation(JNIEnv* env, jobject obj, jfloat newVal);
    JNIEXPORT jfloat JNICALL Java_com_gvitech_android_RenderControl_getStereoScreenDistance(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_setStereoScreenDistance(JNIEnv* env, jobject obj, jfloat newVal);
    
    JNIEXPORT jfloat JNICALL Java_com_gvitech_android_RenderControl_getOculusInterpupillaryDistance(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_setOculusInterpupillaryDistance(JNIEnv* env, jobject obj, jfloat newVal);
    JNIEXPORT jfloat JNICALL Java_com_gvitech_android_RenderControl_getOculusProjectionCenterOffset(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_RenderControl_setOculusProjectionCenterOffset(JNIEnv* env, jobject obj, jfloat newVal);
    
    JNIEXPORT jint JNICALL Java_com_gvitech_android_RenderControl_setLicenseInfoT(JNIEnv* env, jobject obj, jstring licFile, jstring signature, jint year, jint month, jint day);

    
    
    /**********************************************************
     * IViewport
     *********************************************************/
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_IViewport_getLogoVisible(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IViewport_setLogoVisible(JNIEnv* env, jobject obj, jboolean newVal);
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_IViewport_getCompassVisible(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IViewport_setCompassVisible(JNIEnv* env, jobject obj, jboolean newVal);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IViewport_setCompassOffset(JNIEnv* env, jobject obj, jfloat ox, jfloat oy);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IViewport_setThumbtackOffset(JNIEnv* env, jobject obj, jfloat ox, jfloat oy);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IViewport_getViewportModeT(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IViewport_setViewportMode(JNIEnv* env, jobject obj, jobject newVal);

    
    /**********************************************************
     * Camera
     *********************************************************/
    JNIEXPORT jdouble JNICALL Java_com_gvitech_android_ICamera_getFlyTime(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_setFlyTime(JNIEnv* env, jobject obj, jdouble newVal);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_setCameraT(JNIEnv* env, jobject obj, jdouble x, jdouble y, jdouble z, jdouble heading, jdouble tilt, jdouble roll, jobject flags);
    JNIEXPORT jdoubleArray JNICALL Java_com_gvitech_android_ICamera_getCamera(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_lookAtT(JNIEnv* env, jobject obj, jdouble x, jdouble y, jdouble z, jdouble distance, jdouble heading, jdouble tilt, jdouble roll);
    JNIEXPORT jdoubleArray JNICALL Java_com_gvitech_android_ICamera_getAimingAnglesT(JNIEnv* env, jobject obj, jdouble x1, jdouble y1, jdouble z1, jdouble x2, jdouble y2, jdouble z2);
    
    JNIEXPORT jint JNICALL Java_com_gvitech_android_ICamera_getFlyModeT(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_setFlyMode(JNIEnv* env, jobject obj, jobject newVal);
    JNIEXPORT jfloat JNICALL Java_com_gvitech_android_ICamera_getWalkSpeed(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_setWalkSpeed(JNIEnv* env, jobject obj, jfloat newVal);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_ICamera_getCollisionDetectionModeT(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_setCollisionDetectionMode(JNIEnv* env, jobject obj, jobject newVal);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_flyToObject(JNIEnv* env, jobject obj, jint oid, jobject code);
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_ICamera_screenToWorldT(JNIEnv* env, jobject obj, jint x, jint y, jobject ix, jobject iy, jobject iz, jobject iObject, jobject iFid);
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_ICamera_worldToScreenT(JNIEnv* env, jobject obj, jdouble worldX, jdouble worldY, jdouble worldZ, jobject screenX, jobject screenY, jint mode, jobject boolInScreen);
    JNIEXPORT jfloat JNICALL Java_com_gvitech_android_ICamera_getNearClipPlane(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_setNearClipPlane(JNIEnv* env, jobject obj, jfloat newVal);
    JNIEXPORT jfloat JNICALL Java_com_gvitech_android_ICamera_getFarClipPlane(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_setFarClipPlane(JNIEnv* env, jobject obj, jfloat newVal);
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_ICamera_getAutoClipPlane(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_setAutoClipPlane(JNIEnv* env, jobject obj, jboolean newVal);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_stop(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_enterOrthoModeT(JNIEnv* env, jobject obj, jdouble xmin, jdouble xmax, jdouble ymin, jdouble ymax, jdouble zmin, jdouble zmax, jdouble heading, jdouble tilt, jdouble roll);
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_ICamera_isOrthoMode(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_leaveOrthoMode(JNIEnv* env, jobject obj);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_ICamera_getWalkModeT(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICamera_setWalkMode(JNIEnv* env, jobject obj, jobject newVal);

    
    /**********************************************************
     * RObject
     *********************************************************/
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IRObject_getTypeT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IRObject_getGuidT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IRObject_getClientDataT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IRObject_setClientDataT(JNIEnv* env, jobject obj, jint oid, jstring newVal);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IRObject_getAttributeMaskT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IRObject_setAttributeMaskT(JNIEnv* env, jobject obj, jint oid, jobject newVal);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IRObject_getNameT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IRObject_setNameT(JNIEnv* env, jobject obj, jint oid, jstring newVal);

    
    
    /**********************************************************
     * Label
     *********************************************************/
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_ILabel_getTextT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ILabel_setTextT(JNIEnv* env, jobject obj, jint oid, jstring newVal);
    JNIEXPORT jdouble JNICALL Java_com_gvitech_android_ILabel_getXT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT jdouble JNICALL Java_com_gvitech_android_ILabel_getYT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT jdouble JNICALL Java_com_gvitech_android_ILabel_getZT(JNIEnv* env, jobject obj, jint oid);

    
    /**********************************************************
     * RenderPoint
     *********************************************************/
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IRenderPoint_getImageNameT(JNIEnv* env, jobject obj, jint oid);    
    
    
    /**********************************************************
     * ReferencePlane
     *********************************************************/
    JNIEXPORT jdouble JNICALL Java_com_gvitech_android_IReferencePlane_getPlaneHeight(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IReferencePlane_setPlaneHeight(JNIEnv* env, jobject obj, jdouble newVal);

    
    /**********************************************************
     * SkyBox
     *********************************************************/
    JNIEXPORT void JNICALL Java_com_gvitech_android_ISkyBox_setImagePath(JNIEnv* env, jobject obj, jobject index, jstring imagePath);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_ISkyBox_getWeatherT(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ISkyBox_setWeather(JNIEnv* env, jobject obj, jobject weatherType);
    
    JNIEXPORT jint JNICALL Java_com_gvitech_android_ISkyBox_getFogModeT(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ISkyBox_setFogMode(JNIEnv* env, jobject obj, jobject fogMode);
    JNIEXPORT jfloat JNICALL Java_com_gvitech_android_ISkyBox_getFogStartDistance(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ISkyBox_setFogStartDistance(JNIEnv* env, jobject obj, jfloat newVal);
    JNIEXPORT jfloat JNICALL Java_com_gvitech_android_ISkyBox_getFogEndDistance(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ISkyBox_setFogEndDistance(JNIEnv* env, jobject obj, jfloat newVal);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_ISkyBox_getFogColor(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ISkyBox_setFogColor(JNIEnv* env, jobject obj, jint color);
    
    
    /**********************************************************
     * Renderable
     *********************************************************/
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IRenderable_getDepthTestModeT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderable_setDepthTestModeT(JNIEnv* env, jobject obj, jint oid, jobject newVal);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IRenderable_getVisibleMaskT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderable_setVisibleMaskT(JNIEnv* env, jobject obj, jint oid, jobject newVal);
    JNIEXPORT jdouble JNICALL Java_com_gvitech_android_IRenderable_getMaxVisibleDistanceT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderable_setMaxVisibleDistanceT(JNIEnv* env, jobject obj, jint oid, jdouble newVal);
    JNIEXPORT jdouble JNICALL Java_com_gvitech_android_IRenderable_getViewingDistanceT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderable_setViewingDistanceT(JNIEnv* env, jobject obj, jint oid, jdouble newVal);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderable_highlightT(JNIEnv* env, jobject obj, jint oid, jint color);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderable_unhighlightT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IRenderable_getMouseSelectMaskT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderable_setMouseSelectMaskT(JNIEnv* env, jobject obj, jint oid, jobject newVal);
    JNIEXPORT jfloat JNICALL Java_com_gvitech_android_IRenderable_getMinVisiblePixelsT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderable_setMinVisiblePixelsT(JNIEnv* env, jobject obj, jint oid, jfloat newVal);
    JNIEXPORT jdoubleArray JNICALL Java_com_gvitech_android_IRenderable_getEnvelopeT(JNIEnv* env, jobject obj, jint oid);
    
    
    /**********************************************************
     * RenderGeometry
     *********************************************************/
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IRenderGeometry_getFdeGeometryT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderGeometry_setFdeGeometryT(JNIEnv* env, jobject obj, jint oid, jstring wkt);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderGeometry_setXT(JNIEnv* env, jobject obj, jint oid, jdouble x);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderGeometry_setYT(JNIEnv* env, jobject obj, jint oid, jdouble y);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IRenderGeometry_setZT(JNIEnv* env, jobject obj, jint oid, jdouble z);
    
    
    /**********************************************************
     * FeatureLayer
     *********************************************************/
    JNIEXPORT void JNICALL Java_com_gvitech_android_IFeatureLayer_highlightFeatureT(JNIEnv* env, jobject obj, jint layerId, jint featureId, jint color);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IFeatureLayer_unhighlightAll(JNIEnv* env, jobject obj);
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_IFeatureLayer_searchByIdT(JNIEnv* env, jobject obj, jint layerId, jint featureId);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IFeatureLayer_getFeatureClassIdT(JNIEnv* env, jobject obj, jint layerId);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IFeatureLayer_getGeometryFieldNameT(JNIEnv* env, jobject obj, jint layerId);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IFeatureLayer_getDataSourceConnectionStringT(JNIEnv* env, jobject obj, jint layerId);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IFeatureLayer_getDataSetNameT(JNIEnv* env, jobject obj, jint layerId);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IFeatureLayer_getFeatureClassNameT(JNIEnv* env, jobject obj, jint layerId);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IFeatureLayer_hiddenFeaturesT(JNIEnv* env, jobject obj, jint layerId, jintArray featureIds);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IFeatureLayer_createRenderModelPointT(JNIEnv* env, jobject obj, jint layerId, jint featureId);
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_IFeatureLayer_searchT(JNIEnv* env, jobject obj, jint layerId, jlong filter);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IFeatureLayer_setGroupVisibleMaskT(JNIEnv* env, jobject obj, jint layerId, jint groupId, jobject newVal);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IFeatureLayer_getGroupVisibleMaskT(JNIEnv* env, jobject obj, jint layerId, jint groupId);
    
    /**********************************************************
     * CameraTour
     *********************************************************/
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_ICameraTour_fromXmlT(JNIEnv* env, jobject obj, jint oid, jstring xmlStr);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICameraTour_playT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICameraTour_pauseT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICameraTour_stopT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT jdouble JNICALL Java_com_gvitech_android_ICameraTour_getTotalTimeT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_ICameraTour_getWaypointsNumberT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT jdouble JNICALL Java_com_gvitech_android_ICameraTour_getTimeT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICameraTour_setTimeT(JNIEnv* env, jobject obj, jint oid, jdouble newVal);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_ICameraTour_getIndexT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICameraTour_setIndexT(JNIEnv* env, jobject obj, jint oid, jint newVal);
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_ICameraTour_getAutoRepeatT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICameraTour_setAutoRepeatT(JNIEnv* env, jobject obj, jint oid, jboolean newVal);
 
    /**********************************************************
     * MotionPath
     *********************************************************/
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_IMotionPath_fromXmlT(JNIEnv* env, jobject obj, jint oid, jstring xmlStr);
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_IMotionPath_playT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_IMotionPath_pauseT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_IMotionPath_stopT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT jdouble JNICALL Java_com_gvitech_android_IMotionPath_getTotalDurationT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IMotionPath_getWaypointsNumberT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT jdouble JNICALL Java_com_gvitech_android_IMotionPath_getTimeT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IMotionPath_setTimeT(JNIEnv* env, jobject obj, jint oid, jdouble newVal);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IMotionPath_getIndexT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IMotionPath_setIndexT(JNIEnv* env, jobject obj, jint oid, jint newVal);
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_IMotionPath_getAutoRepeatT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICameraTour_setAutoRepeatT(JNIEnv* env, jobject obj, jint oid, jboolean newVal);
    

    /**********************************************************
     * I3DTileLayer
     *********************************************************/
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_I3DTileLayer_getGrayShowT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_I3DTileLayer_setGrayShowT(JNIEnv* env, jobject obj, jint oid, jboolean newVal);
    JNIEXPORT jfloat JNICALL Java_com_gvitech_android_I3DTileLayer_getGrayScalarT(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT void JNICALL Java_com_gvitech_android_I3DTileLayer_setGrayScalarT(JNIEnv* env, jobject obj, jint oid, jfloat newVal);

    
    
    /**********************************************************
     * ObjectManager
     *********************************************************/
    JNIEXPORT jintArray Java_com_gvitech_android_IObjectManager_createFeatureLayerT(JNIEnv* env, jobject obj, jstring sdbPath, jlong geometryRender);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IObjectManager_createCameraTourT(JNIEnv* env, jobject obj);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IObjectManager_createMotionPathT(JNIEnv* env, jobject obj);
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_IObjectManager_deleteObject(JNIEnv* env, jobject obj, jint oid);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IObjectManager_createLabelT(JNIEnv* env, jobject obj, jdouble x, jdouble y, jdouble z, jstring text, jint textColor, jint textSize, jint bgColor, jdouble verticalOffset);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IObjectManager_create3DTileLayerT(JNIEnv* env, jobject obj, jstring layerInfo, jstring password);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IObjectManager_createRenderPolylineT(JNIEnv* env, jobject obj, jdoubleArray arrPoints, jint lineColor, jfloat lineWidth);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IObjectManager_createRenderPolygonT(JNIEnv* env, jobject obj, jdoubleArray arrPoints, jint fillColor);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IObjectManager_createRenderPointT(JNIEnv* env, jobject obj, jdouble x, jdouble y, jdouble z, jstring imagePath, jint size);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IObjectManager_getObjectByIdT(JNIEnv* env, jobject obj, jint oid);
    
    
    /**********************************************************
     * CacheManager
     *********************************************************/
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_ICacheManager_getFileCacheEnabled(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICacheManager_setFileCacheEnabled(JNIEnv* env, jobject obj, jboolean newVal);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_ICacheManager_getFileCachePath(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICacheManager_setFileCachePath(JNIEnv* env, jobject obj, jstring newVal);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_ICacheManager_getTileCacheFileName(JNIEnv* env, jobject obj, jstring newVal);

    
    /**********************************************************
     * ExportManager
     *********************************************************/
    JNIEXPORT jintArray JNICALL Java_com_gvitech_android_IExportManager_exportImageT(JNIEnv* env, jobject obj, jstring newVal);

    
    /**********************************************************
     * GeometryRender
     *********************************************************/
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IGeometryRender_getRenderTypeT(JNIEnv* env, jobject obj, jlong geometryRender);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IGeometryRender_setRenderGroupFieldT(JNIEnv* env, jobject obj, jlong geometryRender, jstring newVal);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IGeometryRender_getRenderGroupFieldT(JNIEnv* env, jobject obj, jlong geometryRender);

    /**********************************************************
     * SimpleGeometryRender
     *********************************************************/
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_ISimpleGeometryRender_createT(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ISimpleGeometryRender_setSymbolT(JNIEnv* env, jobject obj, jlong geometryRender, jlong symbol);

    /**********************************************************
     * GeometrySymbol
     *********************************************************/
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IGeometrySymbol_getSymbolTypeT(JNIEnv* env, jobject obj, jlong geometrySymbol);

    /**********************************************************
     * CurveSymbol
     *********************************************************/
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_ICurveSymbol_createT(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICurveSymbol_setColorT(JNIEnv* env, jobject obj, jlong geometrySymbol, jint color);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICurveSymbol_setWidthT(JNIEnv* env, jobject obj, jlong geometrySymbol, jfloat width);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICurveSymbol_setRepeatLengthT(JNIEnv* env, jobject obj, jlong geometrySymbol, jfloat repeatLength);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ICurveSymbol_setImageNameT(JNIEnv* env, jobject obj, jlong geometrySymbol, jstring imageName);

    /**********************************************************
     * SurfaceSymbol
     *********************************************************/
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_ISurfaceSymbol_createT(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ISurfaceSymbol_setColorT(JNIEnv* env, jobject obj, jlong geometrySymbol, jint color);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ISurfaceSymbol_setBoundarySymbolT(JNIEnv* env, jobject obj, jlong geometrySymbol, jlong symbol);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ISurfaceSymbol_setImageNameT(JNIEnv* env, jobject obj, jlong geometrySymbol, jstring imageName);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ISurfaceSymbol_setRepeatLengthUT(JNIEnv* env, jobject obj, jlong geometrySymbol, jfloat repeatLength);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ISurfaceSymbol_setRepeatLengthVT(JNIEnv* env, jobject obj, jlong geometrySymbol, jfloat repeatLength);
    
    /**********************************************************
     * PointSymbol
     *********************************************************/
    JNIEXPORT void JNICALL Java_com_gvitech_android_IPointSymbol_setColorT(JNIEnv* env, jobject obj, jlong geometrySymbol, jint color);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IPointSymbol_setSizeT(JNIEnv* env, jobject obj, jlong geometrySymbol, jint size);
    
    /**********************************************************
     * SimplePointSymbol
     *********************************************************/
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_ISimplePointSymbol_createT(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ISimplePointSymbol_setFillColorT(JNIEnv* env, jobject obj, jlong geometrySymbol, jint color);
    JNIEXPORT void JNICALL Java_com_gvitech_android_ISimplePointSymbol_setOutlineColorT(JNIEnv* env, jobject obj, jlong geometrySymbol, jint color);
    
    /**********************************************************
     * ImagePointSymbol
     *********************************************************/
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_IImagePointSymbol_createT(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IImagePointSymbol_setImageNameT(JNIEnv* env, jobject obj, jlong geometrySymbol, jstring imageName);
    
    /**********************************************************
     * ConnectionInfo
     *********************************************************/
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_IConnectionInfo_createT(JNIEnv* env, jobject obj);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IConnectionInfo_getTypeT(JNIEnv* env, jobject obj, jlong connInfo);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IConnectionInfo_setTypeT(JNIEnv* env, jobject obj, jlong connInfo, jobject newVal);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IConnectionInfo_freePtrObject(JNIEnv* env, jobject obj, jlong connInfo);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IConnectionInfo_getDataBaseNameT(JNIEnv* env, jobject obj, jlong connInfo);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IConnectionInfo_setDataBaseNameT(JNIEnv* env, jobject obj, jlong connInfo, jstring newVal);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IConnectionInfo_setServerT(JNIEnv* env, jobject obj, jlong connInfo, jstring newVal);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IConnectionInfo_setPortT(JNIEnv* env, jobject obj, jlong connInfo, jint newVal);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IConnectionInfo_setUserT(JNIEnv* env, jobject obj, jlong connInfo, jstring newVal);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IConnectionInfo_setPwdT(JNIEnv* env, jobject obj, jlong connInfo, jstring newVal);
    
    /**********************************************************
     * DataSourceFactory
     *********************************************************/
    JNIEXPORT jboolean JNICALL Java_com_gvitech_android_IDataSourceFactory_hasDataSourceT(JNIEnv* env, jobject obj, jlong connInfo);
    JNIEXPORT jobjectArray JNICALL Java_com_gvitech_android_IDataSourceFactory_getDataBaseNamesT(JNIEnv* env, jobject obj, jlong connInfo, jboolean bOnlyFdb, jobject iDbCounts);
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_IDataSourceFactory_openDataSourceT(JNIEnv* env, jobject obj, jlong connInfo);
    
    /**********************************************************
     * DataSource
     *********************************************************/
    JNIEXPORT jobjectArray JNICALL Java_com_gvitech_android_IDataSource_getFeatureDatasetNamesT(JNIEnv* env, jobject obj, jlong dataSource, jobject iDsetCounts);
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_IDataSource_openFeatureDatasetT(JNIEnv* env, jobject obj, jlong dataSource, jstring newVal);
    
    /**********************************************************
     * FeatureDataset
     *********************************************************/
    JNIEXPORT jobjectArray JNICALL Java_com_gvitech_android_IFeatureDataset_getNamesByTypeT(JNIEnv* env, jobject obj, jlong dataset, jobject iTableCounts, jobject typeVal);
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_IFeatureDataset_openFeatureClassT(JNIEnv* env, jobject obj, jlong dataset, jstring newVal);
    
    /**********************************************************
     * FeatureClass
     *********************************************************/
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_IFeatureClass_searchT(JNIEnv* env, jobject obj, jlong featureclass, jlong filter, jboolean bReuseRow);
    
    /**********************************************************
     * QueryFilter
     *********************************************************/
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_IQueryFilter_createT(JNIEnv* env, jobject obj);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IQueryFilter_getWhereClauseT(JNIEnv* env, jobject obj, jlong queryFilter);
    JNIEXPORT void JNICALL Java_com_gvitech_android_IQueryFilter_setWhereClauseT(JNIEnv* env, jobject obj, jlong queryFilter, jstring newVal);
    
    /**********************************************************
     * FdeCursor
     *********************************************************/
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_IFdeCursor_nextRowT(JNIEnv* env, jobject obj, jlong fdecursor);
    
    /**********************************************************
     * RowBufferCollection
     *********************************************************/
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IRowBufferCollection_getCountT(JNIEnv* env, jobject obj, jlong buffercollection);
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_IRowBufferCollection_getRowBufferT(JNIEnv* env, jobject obj, jlong buffercollection, jint index);
    
    /**********************************************************
     * RowBuffer
     *********************************************************/
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IRowBuffer_getFieldCountT(JNIEnv* env, jobject obj, jlong rowbuffer);
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_IRowBuffer_getFieldsT(JNIEnv* env, jobject obj, jlong rowbuffer);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IRowBuffer_fieldIndexT(JNIEnv* env, jobject obj, jlong rowbuffer, jstring newVal);
    JNIEXPORT jbyte JNICALL Java_com_gvitech_android_IRowBuffer_getInt8T(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos);
    JNIEXPORT jshort JNICALL Java_com_gvitech_android_IRowBuffer_getInt16T(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IRowBuffer_getInt32T(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos);
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_IRowBuffer_getInt64T(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IRowBuffer_getStringT(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos);
    JNIEXPORT jfloat JNICALL Java_com_gvitech_android_IRowBuffer_getFloatT(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos);
    JNIEXPORT jdouble JNICALL Java_com_gvitech_android_IRowBuffer_getDoubleT(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IRowBuffer_getDateTimeT(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IRowBuffer_getBlobT(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IRowBuffer_getGeometryT(JNIEnv* env, jobject obj, jlong rowbuffer, jint pos);

    
    /**********************************************************
     * FieldInfoCollection
     *********************************************************/
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IFieldInfoCollection_getCountT(JNIEnv* env, jobject obj, jlong fieldcollection);
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_IFieldInfoCollection_getFieldInfoT(JNIEnv* env, jobject obj, jlong fieldcollection, jint index);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IFieldInfoCollection_indexOfT(JNIEnv* env, jobject obj, jlong fieldcollection, jstring newVal);
    
    /**********************************************************
     * FieldInfo
     *********************************************************/
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IFieldInfo_getFieldTypeT(JNIEnv* env, jobject obj, jlong fieldinfo);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IFieldInfo_getNameT(JNIEnv* env, jobject obj, jlong fieldinfo);
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_IFieldInfo_getDomainT(JNIEnv* env, jobject obj, jlong fieldinfo);
    
    /**********************************************************
     * Domain
     *********************************************************/
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IDomain_getFieldTypeT(JNIEnv* env, jobject obj, jlong domian);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_IDomain_getDomainTypeT(JNIEnv* env, jobject obj, jlong domain);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_IDomain_getNameT(JNIEnv* env, jobject obj, jlong domain);
    
    /**********************************************************
     * CodedValueDomain
     *********************************************************/
    JNIEXPORT jint JNICALL Java_com_gvitech_android_ICodedValueDomain_getCodeCountT(JNIEnv* env, jobject obj, jlong codedValueDomain);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_ICodedValueDomain_getCodeNameT(JNIEnv* env, jobject obj, jlong codedValueDomain, jint pos);
    JNIEXPORT jbyte JNICALL Java_com_gvitech_android_ICodedValueDomain_getInt8T(JNIEnv* env, jobject obj, jlong codedValueDomain, jint pos);
    JNIEXPORT jshort JNICALL Java_com_gvitech_android_ICodedValueDomain_getInt16T(JNIEnv* env, jobject obj, jlong codedValueDomain, jint pos);
    JNIEXPORT jint JNICALL Java_com_gvitech_android_ICodedValueDomain_getInt32T(JNIEnv* env, jobject obj, jlong codedValueDomain, jint pos);
    JNIEXPORT jlong JNICALL Java_com_gvitech_android_ICodedValueDomain_getInt64T(JNIEnv* env, jobject obj, jlong codedValueDomain, jint pos);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_ICodedValueDomain_getStringT(JNIEnv* env, jobject obj, jlong codedValueDomain, jint pos);
    JNIEXPORT jfloat JNICALL Java_com_gvitech_android_ICodedValueDomain_getFloatT(JNIEnv* env, jobject obj, jlong codedValueDomain, jint pos);
    JNIEXPORT jdouble JNICALL Java_com_gvitech_android_ICodedValueDomain_getDoubleT(JNIEnv* env, jobject obj, jlong codedValueDomain, jint pos);
    JNIEXPORT jstring JNICALL Java_com_gvitech_android_ICodedValueDomain_getDateTimeT(JNIEnv* env, jobject obj, jlong codedValueDomain, jint pos);
    
    
    
    
    JNIEXPORT jint Java_com_gvitech_android_TestNative_add(JNIEnv* env, jobject obj, jint x, jint y);

};

#endif // JNIAPI_H
