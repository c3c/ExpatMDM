## printf preload

This little tool can be preloaded into the VM to catch all printf calls by native libaries. They are then redirected to adb log output.
Took me some time to figure out that printf-calls with only one argument are optimized to "puts" by the compiler...
Though it makes sense, since printf's signature expects a format string followed by a va_list.

Compile:
```
export PATH="/path/to/ndk:$PATH"
ndk-build NDK_PROJECT_PATH=. APP_BUILD_SCRIPT=./Android.mk
```

Then inside the java app, preload it before any other objects, or use the wrap.x.x property ([more info](http://cedricvb.be/post/intercepting-android-native-library-calls/)).
```
System.load("/path/to/lib/libprintf.so");
```
