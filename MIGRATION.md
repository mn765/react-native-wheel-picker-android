# AndroidX Migration Guide

This library has been fully migrated to AndroidX and modern Android development practices.

## What Changed

### Build Configuration
- **SDK Versions**: Updated to `compileSdkVersion 34`, `targetSdkVersion 34`, and `minSdkVersion 21`
- **AndroidX**: Migrated from Android Support Libraries to AndroidX
  - `com.android.support:appcompat-v7` → `androidx.appcompat:appcompat:1.6.1`
- **Gradle**: Compatible with Gradle 8+
- **Namespace**: Added `namespace 'com.wheelpicker'` in build.gradle (required for modern Android)
- **Build Tools**: Removed obsolete `buildToolsVersion` (auto-detected by AGP)

### Memory Leak Fixes
All classes have been updated to prevent memory leaks:

1. **MessageHandler.java**
   - Now uses `WeakReference<LoopView>` instead of strong reference
   - Explicitly uses `Looper.getMainLooper()`
   - Replaced `while(true)` with proper `switch-case`

2. **LoopViewGestureListener.java**
   - Uses `WeakReference<LoopView>` instead of strong reference
   - Null-safety checks in all callbacks

3. **MTimer.java**
   - Uses `WeakReference<LoopView>` instead of strong reference
   - Added divide-by-zero guards
   - Null-safety checks for handler

4. **LoopTimerTask.java**
   - Uses `WeakReference<LoopView>` instead of strong reference
   - Added null checks for arrayList access
   - Null-safety checks for handler

5. **LoopRunnable.java**
   - Uses `WeakReference<LoopView>` instead of strong reference
   - Comprehensive null-safety checks

6. **LoopView.java**
   - Added generics: `ArrayList<String>` instead of raw `ArrayList`
   - Added `onDetachedFromWindow()` to properly cleanup executors and handlers
   - Added null-check guards in `onDraw()` and `initData()`
   - Added divide-by-zero guards in `smoothScroll()`
   - Removed obsolete SDK version checks (API 11)
   - Proper executor lifecycle management

### Code Quality Improvements
- **Type Safety**: All ArrayList usage now properly typed with generics
- **Null Safety**: Comprehensive null checks throughout
- **Code Style**: Fixed spacing and formatting issues
- **WheelPickerManager**: Fixed unsafe ArrayList casts, now properly converts integers to strings

## Requirements

### For Library Consumers
- **React Native**: 0.60 or higher (autolinking support)
- **Android**: 
  - `minSdkVersion 21` (Android 5.0+)
  - `compileSdkVersion 34` recommended
  - AndroidX enabled in `gradle.properties`:
    ```properties
    android.useAndroidX=true
    android.enableJetifier=true
    ```

### For Development
- **Gradle**: 8.0+ recommended
- **Android Gradle Plugin**: 8.1.0+
- **JDK**: 11 or higher

## Migration Steps for Apps Using This Library

### 1. Update gradle.properties (if not already done)
```properties
android.useAndroidX=true
android.enableJetifier=true
```

### 2. Update your app's build.gradle
```gradle
android {
    compileSdkVersion 34
    
    defaultConfig {
        minSdkVersion 21  // or higher
        targetSdkVersion 34
    }
    
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_11
        targetCompatibility JavaVersion.VERSION_11
    }
}
```

### 3. Update dependencies
If you manually manage dependencies, update them to AndroidX versions. For most React Native projects with autolinking, this is handled automatically.

## Breaking Changes

**None** - The public API remains unchanged. All changes are internal improvements for memory management, type safety, and AndroidX compatibility.

## Verification

After updating:
1. Clean your build: `cd android && ./gradlew clean`
2. Rebuild: `cd android && ./gradlew assembleDebug`
3. Test your app thoroughly, especially:
   - Screen rotations (tests lifecycle management)
   - Repeated navigation to/from screens with pickers (tests memory leaks)
   - Long-running app sessions (tests executor cleanup)

## Troubleshooting

### "Manifest merger failed" error
Make sure your app has migrated to AndroidX. Run:
```bash
npx jetifier
```

### Build fails with "Namespace not specified"
Update your Android Gradle Plugin to 8.0+ or add namespace to your app's build.gradle:
```gradle
android {
    namespace 'com.yourapp'
    // ...
}
```

### Memory warnings or crashes
If you see memory-related issues:
1. Ensure you're using the latest version of this library
2. Check that your app properly handles component unmounting
3. Verify no circular references in your React Native code

## Credits

This migration ensures the library is compatible with modern React Native (0.70+) and Android development practices while maintaining backward compatibility with the existing API.
