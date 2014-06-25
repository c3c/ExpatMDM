#include <stdio.h>
#include <stdarg.h>
#include <jni.h>
#include <android/log.h>
#include <dlfcn.h>
#include <stdlib.h>

// Guess what, "printf" is often being optimized by the compiler to "puts" if it's not being fed any arguments

extern "C" int printf (__const char *__restrict __format, ...)
{
	int ret_status = 0;

	va_list args;
	va_start(args,__format);
	ret_status = vprintf(__format, args);
	__android_log_vprint(ANDROID_LOG_INFO, "printf", __format, args);
	va_end(args);

	return ret_status;
}

int puts(const char* s) {
	int (*original_puts)(const char*) = dlsym(RTLD_NEXT, "puts");
	__android_log_print(ANDROID_LOG_INFO, "printf", s, 0);
	return original_puts(s);
}
