// defines which exploit/patch number the JNI interface should call
// should be incremented for each new CVE
#define EXPAT_NUM 1

#define STR_HELPER(x) #x
#define STR(x) STR_HELPER(x)
#define __EXPAT_NUM__ STR(EXPAT_NUM)
