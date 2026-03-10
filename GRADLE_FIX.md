# Fix: GRADLE_USER_HOME is Unknown Error

## Problem
The error "Base: GRADLE_USER_HOME is unknown" occurs when Gradle cannot locate its home directory.

## Solutions Applied

### 1. Updated gradle.properties
I've updated your `gradle.properties` file with proper configuration.

### 2. Manual Fixes (Choose One)

#### Option A: Set Environment Variable (Recommended)

**For Windows:**

1. **Open System Environment Variables:**
   - Press `Win + X` and select "System"
   - Click "Advanced system settings"
   - Click "Environment Variables"

2. **Add GRADLE_USER_HOME:**
   - Under "User variables", click "New"
   - Variable name: `GRADLE_USER_HOME`
   - Variable value: `C:\Users\patil\.gradle`
   - Click OK

3. **Restart Android Studio**

**Quick Command (Run in PowerShell as Administrator):**
```powershell
[System.Environment]::SetEnvironmentVariable('GRADLE_USER_HOME', 'C:\Users\patil\.gradle', 'User')
```

#### Option B: Use Android Studio Settings

1. Open Android Studio
2. Go to **File → Settings** (or Ctrl+Alt+S)
3. Navigate to **Build, Execution, Deployment → Build Tools → Gradle**
4. Set "Gradle user home" to: `C:\Users\patil\.gradle`
5. Click Apply and OK
6. Restart Android Studio

#### Option C: Create gradle.properties in User Home

Create a file at: `C:\Users\patil\.gradle\gradle.properties`

With this content:
```properties
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.caching=true
```

### 3. Clean and Rebuild

After applying any fix above:

1. **In Android Studio:**
   - File → Invalidate Caches → Invalidate and Restart

2. **After restart:**
   - Build → Clean Project
   - Build → Rebuild Project

3. **Or use Terminal:**
   ```bash
   # In project directory
   .\gradlew clean
   .\gradlew build
   ```

## Alternative: Fix Gradle Wrapper

If the issue persists, regenerate the Gradle wrapper:

### Step 1: Delete existing wrapper files
Delete these folders/files:
- `.gradle` folder in project root
- `gradle` folder in project root
- `gradlew` file
- `gradlew.bat` file

### Step 2: Regenerate wrapper in Android Studio
1. Open Terminal in Android Studio
2. Run:
   ```bash
   gradle wrapper --gradle-version 8.2
   ```

### Step 3: Sync project
- File → Sync Project with Gradle Files

## Verification

After applying the fix, verify it works:

1. Open Terminal in Android Studio
2. Run:
   ```bash
   .\gradlew --version
   ```

You should see Gradle version information without errors.

## Common Causes

1. **Missing .gradle folder** - Gradle needs this in user home
2. **Corrupted Gradle cache** - Delete and regenerate
3. **Permission issues** - Run Android Studio as administrator
4. **Antivirus blocking** - Temporarily disable and test
5. **OneDrive sync issues** - Exclude .gradle from sync

## Quick Fix Script

Save this as `fix-gradle.bat` and run it:

```batch
@echo off
echo Fixing Gradle User Home...

REM Set environment variable
setx GRADLE_USER_HOME "%USERPROFILE%\.gradle"

REM Create directory if not exists
if not exist "%USERPROFILE%\.gradle" mkdir "%USERPROFILE%\.gradle"

REM Create gradle.properties
echo org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8 > "%USERPROFILE%\.gradle\gradle.properties"
echo org.gradle.daemon=true >> "%USERPROFILE%\.gradle\gradle.properties"
echo org.gradle.parallel=true >> "%USERPROFILE%\.gradle\gradle.properties"

echo Done! Please restart Android Studio.
pause
```

## If Nothing Works

### Nuclear Option: Complete Reset

1. **Close Android Studio**

2. **Delete Gradle caches:**
   ```
   C:\Users\patil\.gradle
   C:\Users\patil\.android
   ```

3. **Delete project build folders:**
   ```
   YourProject\.gradle
   YourProject\.idea
   YourProject\app\build
   YourProject\build
   ```

4. **Restart Android Studio**

5. **Let it re-download everything**

## Specific to Your Project

Since your project is in OneDrive:
```
C:\Users\patil\OneDrive\Desktop\SmartParentControl
```

OneDrive might be interfering. Try:

1. **Exclude from OneDrive sync:**
   - Right-click project folder
   - Choose "Always keep on this device"
   - Or move project outside OneDrive

2. **Or move project to:**
   ```
   C:\AndroidProjects\SmartParentControl
   ```

## Expected Result

After fix, you should see:
```
✅ Gradle sync successful
✅ Build successful
✅ No GRADLE_USER_HOME errors
```

## Still Having Issues?

If the error persists, provide:
1. Android Studio version
2. Java/JDK version (run `java -version`)
3. Full error log from Build Output
4. Screenshot of error

## Prevention

To avoid this in future:
1. Always use Android Studio's built-in Gradle
2. Don't manually modify Gradle files unless necessary
3. Keep Android Studio updated
4. Use stable Gradle versions
5. Avoid special characters in project path
