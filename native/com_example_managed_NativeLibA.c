#include <jni.h>
#include <stdio.h>
#include "com_example_managed_NativeLibA.h"
#include <android/log.h>

JNIEXPORT void JNICALL Java_com_example_managed_NativeLibA_sayHello(JNIEnv *jenv, jobject jobj) {
#if defined(__arm__)
	const char *cpuArch = "32-bit ARM";
#elif defined(__aarch64__)
	const char *cpuArch = "64-bit ARM";
#elif defined(__i386__)
	const char *cpuArch = "32-bit x86";
#elif defined(__x86_64__)
	const char *cpuArch = "64-bit x86";
#else
	const char *cpuArch = "UNDEFINED";
#endif
   	__android_log_print(ANDROID_LOG_VERBOSE, "HoudiniLives", "Hello from NativeLibA %s", cpuArch);
   return;
}