@echo off
echo ========================================
echo Fixing GRADLE_USER_HOME Error
echo ========================================
echo.

REM Set GRADLE_USER_HOME environment variable
echo Setting GRADLE_USER_HOME environment variable...
setx GRADLE_USER_HOME "%USERPROFILE%\.gradle"

REM Create .gradle directory if it doesn't exist
if not exist "%USERPROFILE%\.gradle" (
    echo Creating .gradle directory...
    mkdir "%USERPROFILE%\.gradle"
)

REM Create gradle.properties in user home
echo Creating gradle.properties in user home...
(
echo org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
echo org.gradle.daemon=true
echo org.gradle.parallel=true
echo org.gradle.caching=true
echo android.useAndroidX=true
echo android.enableJetifier=true
) > "%USERPROFILE%\.gradle\gradle.properties"

echo.
echo ========================================
echo Fix Applied Successfully!
echo ========================================
echo.
echo GRADLE_USER_HOME is now set to: %USERPROFILE%\.gradle
echo.
echo IMPORTANT: Please follow these steps:
echo 1. Close Android Studio completely
echo 2. Restart your computer (or logout and login)
echo 3. Open Android Studio again
echo 4. Open your project
echo 5. Click "Sync Now" when prompted
echo.
echo If the error persists, try:
echo - File ^> Invalidate Caches ^> Invalidate and Restart
echo.
pause
