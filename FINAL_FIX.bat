@echo off
echo ============================================================
echo FINAL FIX FOR GRADLE_USER_HOME ERROR
echo ============================================================
echo.

echo Step 1: Setting GRADLE_USER_HOME environment variable...
setx GRADLE_USER_HOME "%USERPROFILE%\.gradle"
echo Done!
echo.

echo Step 2: Creating .gradle directory...
if not exist "%USERPROFILE%\.gradle" (
    mkdir "%USERPROFILE%\.gradle"
    echo Created: %USERPROFILE%\.gradle
) else (
    echo Already exists: %USERPROFILE%\.gradle
)
echo.

echo Step 3: Creating gradle.properties in user home...
(
echo org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
echo org.gradle.daemon=true
echo org.gradle.parallel=true
echo org.gradle.caching=true
echo android.useAndroidX=true
echo android.enableJetifier=true
) > "%USERPROFILE%\.gradle\gradle.properties"
echo Created: %USERPROFILE%\.gradle\gradle.properties
echo.

echo Step 4: Cleaning project build folders...
if exist ".gradle" (
    echo Deleting .gradle folder...
    rmdir /s /q ".gradle"
)
if exist "app\build" (
    echo Deleting app\build folder...
    rmdir /s /q "app\build"
)
if exist "build" (
    echo Deleting build folder...
    rmdir /s /q "build"
)
echo Done!
echo.

echo ============================================================
echo FIX COMPLETED SUCCESSFULLY!
echo ============================================================
echo.
echo GRADLE_USER_HOME is now set to: %USERPROFILE%\.gradle
echo.
echo CRITICAL: You MUST do the following:
echo.
echo 1. CLOSE Android Studio completely (File ^> Exit)
echo 2. RESTART your computer (very important!)
echo 3. After restart, open Android Studio
echo 4. Open your project
echo 5. Click "Sync Now" when prompted
echo.
echo The error should be completely gone after restart.
echo.
echo If you still see the error after restart:
echo - Open Android Studio
echo - Go to File ^> Settings ^> Build Tools ^> Gradle
echo - Set "Gradle user home" to: %USERPROFILE%\.gradle
echo - Click Apply and OK
echo - Restart Android Studio
echo.
pause
