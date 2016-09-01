
JNI_PATH:= $(call my-dir)

$(call import-module, cxx-stl/gnu-libstdc++)
$(call import-module,android/native_app_glue)

LOCAL_CFLAGS += -D_LARGEFILE_SOURCE -D_FILE_OFFSET_BITS=64

# TPL Library
include $(TPL_HOME)/jpeg-8c/Android.mk
include $(TPL_HOME)/curl-7.26.0/Android.mk
include $(TPL_HOME)/zlib-1.2.5-win32/Android.mk
include $(TPL_HOME)/lua-5.1/Android.mk
include $(TPL_HOME)/jsoncpp-0.5.0/Android.mk
include $(TPL_HOME)/OpenThreads/Android.mk
include $(TPL_HOME)/tinyxml_2_6_1/Android.mk
include $(TPL_HOME)/Jpeg2KDecode/Android.mk
include $(TPL_HOME)/freetype-2.4.9/Android.mk
include $(TPL_HOME)/agg-2.5/Android.mk
include $(TPL_HOME)/lpng155/Android.mk
include $(TPL_HOME)/iconv-android-master/jni/Android.mk
include $(TPL_HOME)/xerces-c-3.1.3/projects/Android/Android.mk

# GCL Library
include $(GCL_HOME)/src/gfx/Android.mk
include $(GCL_HOME)/src/ggt/Android.mk
include $(GCL_HOME)/src/gvImage/Android.mk
include $(GCL_HOME)/src/gvModel/Android.mk
include $(GCL_HOME)/src/EnDecode/Android.mk
include $(GCL_HOME)/src/ReadMriFile/Android.mk
include $(GCL_HOME)/src/ServerClient/ServerClient/Android.mk

# FDE Library
include $(FDE_HOME)/FDECommon/Android.mk
include $(FDE_HOME)/FDEGeometry/Android.mk
include $(FDE_HOME)/FDECore/Android.mk
include $(FDE_HOME)/FDEProvider/ProviderUtil/Android.mk
include $(FDE_HOME)/FDEProvider/SQLite/Android.mk
include $(FDE_HOME)/FDEExtToolPlugin/Android.mk
include $(FDE_PATH)/FDEExtension/TileClient/Android.mk
include $(FDE_PATH)/FDEExtension/GMap/filegmap/Android.mk

# GRC Library
include $(GRC_HOME)/src/RenderSystem/Android.mk
include $(GRC_HOME)/src/RenderSystem_GLES/Android.mk
include $(GRC_HOME)/src/RenderDataEngine/Android.mk
include $(GRC_HOME)/src/RenderEngine/Android.mk
include $(GRC_HOME)/src/RenderControl/Android.mk

# app
include $(JNI_PATH)/CityPadGlobals/Android.mk
