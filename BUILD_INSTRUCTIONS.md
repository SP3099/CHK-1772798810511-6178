# Build Instructions - Smart AI Parental Control System

## Current Status
✅ All source files created (17 activities, 4 models, 4 network classes)
✅ All layout files created (15 XML layouts)
✅ AndroidManifest.xml configured with all permissions and components
✅ Dependencies configured in app/build.gradle
✅ Removed problematic launcher icon XMLs (ic_launcher.xml, ic_launcher_round.xml)
✅ SettingsActivity added to manifest

## Build Steps

### Option 1: Using Gradle Command Line (Recommended)
```bash
# Clean the project
.\gradlew clean

# Build debug APK
.\gradlew assembleDebug

# If successful, APK will be at:
# app/build/outputs/apk/debug/app-debug.apk
```

### Option 2: Using Android Studio
1. Open Android Studio
2. Click **File → Sync Project with Gradle Files**
3. Wait for sync to complete
4. Click **Build → Clean Project**
5. Click **Build → Rebuild Project**
6. If successful, click **Run → Run 'app'**

## Known Issues Fixed
1. ✅ Missing color resources (text_on_primary, accent_dark, white, black) - FIXED
2. ✅ Missing launcher icon resources (ic_launcher_foreground) - FIXED by removing adaptive icon XMLs
3. ✅ SettingsActivity not in manifest - FIXED

## Next Steps After Successful Build

### 1. Install APK on Device/Emulator
```bash
# Install the APK
adb install app/build/outputs/apk/debug/app-debug.apk
```

### 2. Grant Required Permissions
The app requires these runtime permissions:
- **Usage Stats Access**: Settings → Apps → Special Access → Usage Access
- **Display Over Other Apps**: Settings → Apps → Special Access → Display Over Other Apps
- **Notifications**: Allow when prompted

### 3. Test Features
Follow the testing guide in `TESTING_GUIDE.md`:
1. Register as Parent
2. Register as Student (on another device or account)
3. Set app limits
4. Track usage
5. Test request system
6. Test device lock

## Troubleshooting

### If Build Fails with "GRADLE_USER_HOME" Error
Run the fix script:
```bash
.\FINAL_FIX.bat
```
Then restart your computer.

### If Sync Fails
1. Check internet connection
2. Invalidate caches: File → Invalidate Caches / Restart
3. Delete `.gradle` folder and sync again

### If Dependencies Fail to Download
1. Check `gradle.properties` has proper proxy settings (if behind firewall)
2. Try using VPN if in restricted network
3. Check Firebase `google-services.json` is present in `app/` folder

## Project Structure
```
app/src/main/
├── java/com/smartai/parentalcontrol/
│   ├── MainActivity.java
│   ├── LoginActivity.java
│   ├── RegisterActivity.java
│   ├── RoleSelectionActivity.java
│   ├── ParentDashboardActivity.java
│   ├── StudentDashboardActivity.java
│   ├── LimitManagementActivity.java
│   ├── RequestsActivity.java
│   ├── RequestTimeActivity.java
│   ├── BlockOverlayActivity.java
│   ├── DeviceLockedActivity.java
│   ├── SettingsActivity.java
│   ├── UsageTrackingService.java
│   ├── InstallReceiver.java
│   ├── AppLimitAdapter.java
│   ├── AppUsageAdapter.java
│   ├── RequestsAdapter.java
│   ├── models/
│   │   ├── User.java
│   │   ├── AppLimit.java
│   │   ├── UsageLog.java
│   │   └── Request.java
│   └── network/
│       ├── ApiClient.java
│       ├── ApiService.java
│       ├── ApiResponse.java
│       └── AuthInterceptor.java
├── res/
│   ├── layout/ (15 XML files)
│   ├── values/
│   │   ├── colors.xml
│   │   ├── strings.xml
│   │   ├── themes.xml
│   │   ├── styles.xml
│   │   └── dimens.xml
│   └── mipmap-*/ (launcher icons)
└── AndroidManifest.xml
```

## Build Configuration
- **Android Gradle Plugin**: 8.2.0
- **Gradle Version**: 8.5
- **Compile SDK**: 34
- **Target SDK**: 34
- **Min SDK**: 26
- **Java Version**: 1.8

## Dependencies
- AndroidX AppCompat, Material, ConstraintLayout, RecyclerView
- Firebase Auth & Database
- Retrofit 2.9.0 (REST API)
- OkHttp 4.11.0 (HTTP client)
- Gson 2.10.1 (JSON parsing)

## Ready to Build!
Your project is now ready. Run `.\gradlew assembleDebug` to build the APK.
