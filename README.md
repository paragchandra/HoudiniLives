# HoudiniLives

Sample project to understand under what conditions an Android device that supports multiple ABIs (e.g. x86 and ARM) will _actually_ load .so files of these different ABIs. For example, as reported by Build.SUPPORTED_ABIS, a Samsung Chromebook Pro supports both 32-bit ARM and x86 binaries.

## Prerequisites

- NDK r18b. Other versions _should_ work due to simplicity of C code.
- CMake 3.15.3+
- Android SDK, Build Tools, etc.

## Project Structure

Two top-level dirs:
- `managed`: Standard Android app; built with Gradle, can be opened in Android Studio
- `native`: Contains two trivial shared libraries written in C; built with CMake and standalone NDK toolchains

Building the `native` side will compile 32-bit x86 and ARM versions of each library and place them into the canonical locations in the `managed` side so that they can be loaded via `System.loadLibrary`. It will also place renamed copies of all 4 of these files into the `assets` folder, so that they will not be stripped during APK installation.


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

1. During [automatic extraction of native code at install time](https://developer.android.com/ndk/guides/abis#aen), if there are _any_ x86 .so files present in the APK (under the `libs` dir), then _only_ those libraries are unpacked and available for `System.loadLibrary`. Meaning if you provide nativeLibA as x86 and nativeLibB as ARM, then only A will be loaded successfully via `System.loadLibrary`.
2. If you _only_ provide ARM libraries, then the installer will unpack these versions and they will be available for `System.loadLibrary`. And at runtime, you will see messages in logcat from Houdini indicating that these ARM binaries are being loaded via the Native Bridge.
3. 