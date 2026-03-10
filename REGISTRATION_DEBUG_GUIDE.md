# Registration Navigation Debug Guide

## Changes Made

### Added Logging and Progress Dialog

**RegisterActivity.java** now includes:
1. ✅ ProgressDialog to show "Creating account..." while registering
2. ✅ Detailed logging with TAG "RegisterActivity"
3. ✅ Better error messages
4. ✅ Step-by-step navigation logging

## Install Updated APK

```bash
# Uninstall old version
adb uninstall com.smartai.parentalcontrol

# Install new version
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Debug Steps

### Step 1: Enable Logcat Monitoring

Open a terminal and run:
```bash
adb logcat -s RegisterActivity:D Firebase:D AndroidRuntime:E
```

This will show:
- Registration steps
- Firebase operations
- Navigation attempts
- Any errors

### Step 2: Test Registration

1. Launch app
2. Get Started → Select Parent
3. Fill registration form:
   - Name: Test User
   - Email: test123@example.com
   - Password: password123
   - Role: Parent
4. Tap Register button

### Step 3: Watch Logcat Output

You should see logs like:
```
D/RegisterActivity: Starting registration for: test123@example.com as Parent
D/RegisterActivity: Attempting Firebase registration...
D/RegisterActivity: Firebase registration successful
D/RegisterActivity: User ID: abc123...
D/RegisterActivity: Saving user to database...
D/RegisterActivity: User data saved successfully
D/RegisterActivity: Navigating to dashboard for role: Parent
D/RegisterActivity: navigateToDashboard called with role: Parent
D/RegisterActivity: Creating intent for ParentDashboardActivity
D/RegisterActivity: Starting activity...
D/RegisterActivity: Navigation complete
```

### Step 4: Check for Errors

If navigation fails, logcat will show:
- Firebase errors (authentication, database)
- Activity not found errors
- Intent errors

## Common Issues and Solutions

### Issue 1: "Activity not found" Error

**Symptom**: Logcat shows `ActivityNotFoundException`

**Solution**: Verify AndroidManifest.xml has:
```xml
<activity
    android:name=".ParentDashboardActivity"
    android:exported="false" />
<activity
    android:name=".StudentDashboardActivity"
    android:exported="false" />
```

### Issue 2: Firebase Network Error

**Symptom**: "A network error has occurred"

**Solution**:
- Check internet connection
- Verify `google-services.json` is in `app/` folder
- Check Firebase project is active

### Issue 3: Database Permission Denied

**Symptom**: "Permission denied" in logcat

**Solution**: Update Firebase Realtime Database rules:
```json
{
  "rules": {
    ".read": "auth != null",
    ".write": "auth != null"
  }
}
```

### Issue 4: Registration Success but No Navigation

**Symptom**: Toast shows "Registration successful!" but stays on same screen

**Possible Causes**:
1. Dashboard activity crashes immediately
2. Layout file missing for dashboard
3. Intent flags issue

**Debug**:
```bash
# Check for crashes
adb logcat -s AndroidRuntime:E

# Check if activity starts
adb logcat -s ActivityManager:I
```

## Manual Navigation Test

If registration works but navigation doesn't, test navigation manually:

### Test Parent Dashboard

```bash
adb shell am start -n com.smartai.parentalcontrol/.ParentDashboardActivity
```

### Test Student Dashboard

```bash
adb shell am start -n com.smartai.parentalcontrol/.StudentDashboardActivity
```

If these commands work, the activities are fine. The issue is in the navigation code.

## Verify Firebase Data

After registration, check Firebase Console:

1. **Authentication**: https://console.firebase.google.com/
   - Go to Authentication → Users
   - Verify user appears with email

2. **Realtime Database**:
   - Go to Realtime Database → Data
   - Check `/users/{userId}` exists with:
     - name
     - email
     - role
     - createdAt

## Alternative: Simplified Navigation

If issues persist, try this simplified approach in RegisterActivity:

```java
private void navigateToDashboard(String role) {
    try {
        Intent intent;
        if ("Parent".equals(role)) {
            intent = new Intent(this, ParentDashboardActivity.class);
        } else {
            intent = new Intent(this, StudentDashboardActivity.class);
        }
        
        startActivity(intent);
        finish();
        
        Log.d(TAG, "Navigation successful");
    } catch (Exception e) {
        Log.e(TAG, "Navigation failed", e);
        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
```

## Expected Behavior After Fix

1. Tap Register button
2. See "Creating account..." progress dialog
3. Progress dialog dismisses
4. See "Registration successful!" toast
5. **Immediately navigate to Parent Dashboard**
6. Parent Dashboard shows 4 cards:
   - View Student Usage
   - Manage App Limits
   - View Requests
   - Lock Device

## Next Steps

1. Install updated APK
2. Run logcat monitoring
3. Test registration
4. Share logcat output if navigation still fails

---

**APK Location**: `app/build/outputs/apk/debug/app-debug.apk`
