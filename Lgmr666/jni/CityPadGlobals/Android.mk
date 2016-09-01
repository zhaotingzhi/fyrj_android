LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := CityMaker
LOCAL_CPP_FEATURES := exceptions rtti
LOCAL_CFLAGS  += -DSQLITEPROVIDER_STATIC_LIBRARY -D_LARGEFILE_SOURCE -D_FILE_OFFSET_BITS=64
# -Werror
LOCAL_C_INCLUDES := $(GRC_HOME)/include	\
					$(FDE_HOME)/	\
					$(GCL_HOME)/include	\
					$(TPL_HOME)/agg-2.5/include	\
					$(TPL_HOME)/boost_1_43_0	\
					$(TPL_HOME)/OpenThreads/include	\
					$(TPL_HOME)/freetype-2.4.9/include
LOCAL_SRC_FILES := AndroidSDK.cpp
LOCAL_LDLIBS := -llog	\
				-landroid	\
				-lEGL	\
				-lc		\
				-lGLESv2
LOCAL_STATIC_LIBRARIES :=	\
							gnustl_static	\
							android_native_app_glue	\
							RenderControl		\
							RenderEngine		\
							RenderDataEngine	\
							RenderSystem_GLES	\
							RenderSystem	\
							SQLiteProvider	\
							ProviderUtil	\
							FDECore			\
							FDEGeometry		\
							FDECommon		\
                            TileClient      \
                            filegmap        \
							ExtToolPlugin	\
							ServerClient	\
							gvModel			\
							gvImage			\
							ggt				\
							EnDecode		\
							ReadMriFile		\
							gfx				\
							curl			\
#							png				\
							lua				\
							json			\
							OpenThreads		\
							tinyxml			\
							Jpeg2KDecode	\
							agg				\
							freetype		\
#							jpeg			\
							zlib            \
                            xerces          \
                            iconv

include $(BUILD_SHARED_LIBRARY)