# HoudiniLives

Sample project to understand under what conditions an Android device will load native .so files that _are not_ of the device's primary ABI. Many x86 Chromebooks fall into this category, e.g. Samsung Chromebook Pro.

## Prerequisites

- NDK r18b. Other versions _should_ work due to simplicity of C code.
- CMake 3.15.3+
- Android SDK, Build Tools, etc.

## Building

```
cd native
export ANDROID_NDK=<path to your local NDK installation>
./gradlew recreate-toolchains generate build
# At this point you can open the 'managed' project in Android Studio and build/debug from the IDE.
# Or if you prefer:
cd ../managed
./gradlew assembleDebug
```

## Observations

- Using the Samsung Chromebook Pro as an example, the supported ABIs listed at runtime are, in order of preference:
1. x86
2. armeabi-v7a
3. arm
- Due to [automatic extraction of native code at install time][https://developer.android.com/ndk/guides/abis#aen], any