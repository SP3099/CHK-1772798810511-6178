# Testing Guide - Smart AI Parental Control System

## ✅ App is Running Successfully!

Now let's test all features to ensure everything works correctly.

---

## 🧪 Testing Checklist

### 1. Initial Setup & Permissions

#### Grant Required Permissions
When you first run the app, you'll need to grant permissions:

**Usage Access Permission:**
1. App will request Usage Stats access
2. Go to: Settings → Apps → Special app access → Usage access
3. Find "Smart AI Parental Control"
4. Enable the toggle
5. Return to app

**Overlay Permission (for blocking screens):**
1. Settings → Apps → Special app access → Display over other apps
2. Find "Smart AI Parental Control"
3. Enable "Allow display over other apps"

**Notification Permission:**
1. Allow when prompted
2. Or: Settings → Apps → Smart AI Parental Control → Notifications → Enable

---

### 2. Authentication Flow

#### Test Registration
- [ ] Open app
- [ ] Click "Register" or "Create Account"
- [ ] Enter name: "Test Parent"
- [ ] Enter email: "parent@test.com"
- [ ] Enter password: "password123"
- [ ] Select role: "Parent"
- [ ] Click "Register"
- [ ] Verify success message

#### Test Login
- [ ] Enter email: "parent@test.com"
- [ ] Enter password: "password123"
- [ ] Click "Login"
- [ ] Should navigate to Parent Dashboard

#### Test Validation
- [ ] Try empty email → Should show error
- [ ] Try invalid email → Should show error
- [ ] Try short password → Should show error

---

### 3. Parent Dashboard

#### Navigate Dashboard
- [ ] Open Parent Dashboard
- [ ] See 4 cards:
  - 📊 View Student Usage
  - ⏱️ Manage App Limits
  - 📬 View Requests
  - 🔒 Lock Device
- [ ] Click each card → Should show toast message

---

### 4. App Limit Management

#### Create App Limit
- [ ] Click "Manage App Limits"
- [ ] Select an app from dropdown (e.g., "Chrome")
- [ ] Move slider to set time (e.g., 60 minutes)
- [ ] Verify time display updates (e.g., "1 hour")
- [ ] Click "Set Limit"
- [ ] Verify limit appears in list below
- [ ] Check app icon displays correctly

#### Remove App Limit
- [ ] Find a limit in the list
- [ ] Click "Remove" button
- [ ] Verify limit is removed from list

#### Test Slider
- [ ] Move slider to 0 → Should show "No limit"
- [ ] Move to 30 → Should show "30 minutes"
- [ ] Move to 60 → Should show "1 hour"
- [ ] Move to 90 → Should show "1h 30m"

---

### 5. Student Dashboard

#### Register as Student
- [ ] Logout from parent account
- [ ] Register new account:
  - Name: "Test Student"
  - Email: "student@test.com"
  - Password: "password123"
  - Role: "Student"

#### View Usage
- [ ] Login as student
- [ ] See today's screen time card
- [ ] See "Used Apps" list
- [ ] Verify app icons display
- [ ] Verify usage times show

#### Request Extra Time
- [ ] Click "Request Extra Time" button
- [ ] Should navigate to request form

---

### 6. Request System

#### Submit Request (Student)
- [ ] Enter reason: "Need to finish homework assignment"
- [ ] Move slider to select minutes (e.g., 30)
- [ ] Verify time display updates
- [ ] Click "Submit Request"
- [ ] Verify success message
- [ ] App should close request screen

#### View Requests (Parent)
- [ ] Logout and login as parent
- [ ] Click "View Requests" card
- [ ] See list of pending requests
- [ ] Verify request shows:
  - Student name
  - Reason
  - Time requested
  - Timestamp

#### Approve Request
- [ ] Click "Approve" button on a request
- [ ] Request should disappear from list
- [ ] If no more requests, see "No pending requests"

#### Deny Request
- [ ] Click "Deny" button on a request
- [ ] Request should disappear from list

---

### 7. Settings

#### Open Settings
- [ ] Click settings icon (if available)
- [ ] Or navigate to SettingsActivity

#### View Account Info
- [ ] Verify name displays correctly
- [ ] Verify email displays correctly
- [ ] Verify role displays correctly

#### Toggle Preferences
- [ ] Toggle "Enable Notifications" → Should show toast
- [ ] Toggle "Auto-sync Data" → Should show toast
- [ ] Toggle "Daily Usage Reports" → Should show toast

#### Clear Cache
- [ ] Click "Clear Cache"
- [ ] Confirm in dialog
- [ ] Verify success message

#### Logout
- [ ] Click "Logout" button
- [ ] Confirm in dialog
- [ ] Should navigate to Login screen
- [ ] Verify can't go back to dashboard

---

### 8. Background Service

#### Start Usage Tracking
- [ ] Login as student
- [ ] Service should start automatically
- [ ] Check notification bar for "Monitoring app usage"

#### Verify Tracking
- [ ] Use some apps (Chrome, YouTube, etc.)
- [ ] Wait a few minutes
- [ ] Return to Student Dashboard
- [ ] Verify usage times updated

---

### 9. Block Overlay (Manual Test)

Since blocking requires actual usage limits to be exceeded, test manually:

#### Test Block Screen
- [ ] Navigate to BlockOverlayActivity (via code or intent)
- [ ] Verify full-screen overlay displays
- [ ] See message: "App usage limit reached"
- [ ] See app name
- [ ] See "Request Extra Time" button
- [ ] See "Go Back" button
- [ ] Try back button → Should go to home screen

---

### 10. Device Lock (Manual Test)

#### Test Lock Screen
- [ ] Navigate to DeviceLockedActivity
- [ ] Verify full-screen red overlay
- [ ] See message: "Device temporarily locked by parent"
- [ ] Try back button → Should not dismiss
- [ ] Try home button → Should not dismiss (may not work without device admin)

---

### 11. App Installation Detection

#### Test Install Receiver
- [ ] Install a new app from Play Store
- [ ] Check Logcat for "InstallReceiver" logs
- [ ] Should see: "New app installed: [app name]"

---

## 🐛 Common Issues & Solutions

### Issue: App crashes on startup
**Solution:** Check if all activities are registered in AndroidManifest.xml

### Issue: Usage stats not showing
**Solution:** Grant Usage Access permission in Settings

### Issue: Can't see installed apps
**Solution:** Grant QUERY_ALL_PACKAGES permission

### Issue: Overlay not showing
**Solution:** Grant Display over other apps permission

### Issue: Service not running
**Solution:** Check notification permission and foreground service permission

---

## 📊 Expected Behavior

### Parent Account
✅ Can view all student data  
✅ Can set app limits  
✅ Can approve/deny requests  
✅ Can lock device  
✅ Can manage settings  

### Student Account
✅ Can view own usage  
✅ Can request extra time  
✅ Can see app limits  
✅ Gets blocked when limit reached  
✅ Can manage own settings  

---

## 🔍 Debug Tips

### View Logs
In Android Studio:
1. Open Logcat (View → Tool Windows → Logcat)
2. Filter by tag:
   - "UsageTrackingService" - for usage tracking
   - "InstallReceiver" - for app installations
   - "MainActivity" - for main activity logs

### Check Database (Future)
Currently using SharedPreferences:
- Device File Explorer → data/data/com.smartai.parentalcontrol/shared_prefs/
- Check ParentalControlPrefs.xml

### Test API Calls (Future)
When backend is ready:
- Check Logcat for OkHttp logs
- Look for HTTP request/response logs
- Verify API endpoints are correct

---

## ✅ Success Criteria

Your app is working correctly if:

1. ✅ All activities open without crashes
2. ✅ Login/Register works
3. ✅ Parent can navigate dashboard
4. ✅ Student can view usage
5. ✅ App limits can be created
6. ✅ Requests can be submitted
7. ✅ Settings work
8. ✅ Logout works
9. ✅ No critical errors in Logcat

---

## 🚀 Next Steps After Testing

1. **Fix any bugs found**
2. **Implement backend API**
3. **Add database persistence**
4. **Test on multiple devices**
5. **Add more features**
6. **Polish UI/UX**
7. **Prepare for release**

---

## 📝 Test Report Template

After testing, document results:

```
Date: ___________
Tester: ___________
Device: ___________
Android Version: ___________

✅ Passed Tests:
- 
- 

❌ Failed Tests:
- 
- 

🐛 Bugs Found:
- 
- 

💡 Suggestions:
- 
- 
```

---

**Happy Testing! 🎉**

If you find any issues, check the error logs and refer to the troubleshooting guides in README.md and BUILD_FIX.md.
