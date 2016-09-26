LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := base
LOCAL_SRC_FILES := ./hook/obj/local/armeabi/libbase.a
LOCAL_EXPORT_C_INCLUDES := ./base
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
# Disable optimisations to avoid memory layout shuffling
# LOCAL_CFLAGS += -Wall -O0
LOCAL_MODULE := crackme
LOCAL_LDLIBS := -shared -llog
LOCAL_LDFLAGS += -shared
LOCAL_STATIC_LIBRARIES := base
LOCAL_SRC_FILES:= payload.c antidebug/antidebug.c loader.c present/present.c encrypt/encrypt.c \
					encrypt/aes.c my_fwrite_arm.c.arm

LOCAL_CFLAGS := -Wall -O0 -mllvm -fla 
# -mllvm -sub -mllvm -fla -mllvm -bcf
# Hack to keep _e_* symbols when stripping executable
TARGET_STRIP += -w -K _e_*


include $(BUILD_SHARED_LIBRARY)
