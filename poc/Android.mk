LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES := \
  pat_cve_2013_6282.c \
  kallsyms.c \
  ptmx.c \
  backdoor_mmap.c

LOCAL_MODULE := pat_cve_2013_6282
LOCAL_MODULE_TAGS := optional
LOCAL_STATIC_LIBRARIES += libkallsyms
LOCAL_STATIC_LIBRARIES += libdevice_database
LOCAL_STATIC_LIBRARIES += libcutils libc
#LOCAL_LDFLAGS += -static
LOCAL_LDLIBS += -llog

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)

LOCAL_SRC_FILES := \
  exp_cve_2013_6282.c \
  backdoor_mmap.c \
  kallsyms.c \
  ptmx.c \
  mm.c \
  mmap.c \
  build_remap_pfn_range.c

LOCAL_MODULE := exp_cve_2013_6282
LOCAL_MODULE_TAGS := optional
LOCAL_STATIC_LIBRARIES := libdiagexploit
LOCAL_STATIC_LIBRARIES += libperf_event_exploit
LOCAL_STATIC_LIBRARIES += libdevice_database
LOCAL_STATIC_LIBRARIES += libmsm_acdb_exploit
LOCAL_STATIC_LIBRARIES += libfj_hdcp_exploit
LOCAL_STATIC_LIBRARIES += libput_user_exploit
LOCAL_STATIC_LIBRARIES += libfb_mem_exploit
LOCAL_STATIC_LIBRARIES += libz_static
LOCAL_STATIC_LIBRARIES += libcutils libc
#LOCAL_LDFLAGS += -static
LOCAL_LDLIBS += -llog

TOP_SRCDIR := $(abspath $(LOCAL_PATH))
TARGET_C_INCLUDES += \
  $(TOP_SRCDIR)/device_database

include $(BUILD_SHARED_LIBRARY)

include $(call all-makefiles-under,$(LOCAL_PATH))
