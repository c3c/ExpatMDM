LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES := \
  printf.cpp

LOCAL_MODULE := printf
LOCAL_MODULE_TAGS := optional
LOCAL_CFLAGS += -fpic -shared -fno-builtin-printf -D_GNU_SOURCE -fpermissive
LOCAL_LDLIBS += -llog

include $(BUILD_SHARED_LIBRARY)
