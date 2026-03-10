# Smart AI Parental Control System

A comprehensive Android application that enables parents to monitor and manage their children's device usage, set app time limits, and maintain healthy digital habits.

## 📱 Project Description

Smart AI Parental Control System is a dual-role mobile application designed to help parents supervise their children's smartphone usage while giving students visibility into their own screen time. The app provides real-time monitoring, app usage tracking, time limit enforcement, and a request-approval system for additional screen time.

## ✨ Features

### For Parents
- **Dashboard Overview** - Centralized view of all parental control features
- **App Usage Monitoring** - Track student's app usage and screen time in real-time
- **Time Limit Management** - Set daily time limits for specific apps
- **Request Management** - Review and approve/deny student requests for extra time
- **Remote Device Lock** - Lock student's device remotely when needed
- **App Installation Alerts** - Get notified when new apps are installed
- **Usage Reports** - View detailed usage statistics and patterns

### For Students
- **Personal Dashboard** - View today's screen time and app usage
- **Usage Transparency** - See which apps have been used and for how long
- **Request Extra Time** - Submit requests to parents for additional screen time
- **Usage Awareness** - Understand personal device usage patterns

### Security & Control
- **User Authentication** - Secure login and registration system
- **Role-Based Access** - Separate interfaces for parents and students
- **App Blocking** - Automatic blocking when time limits are reached
- **Background Monitoring** - Continuous usage tracking via foreground service
- **Device Lock Screen** - Full-screen lock when parent activates device lock

## 🛠️ Technology Stack

### Core Technologies
- **Language**: Java
- **Platform**: Android (API Level 21+)
- **Architecture**: MVC (Model-View-Controller)

### Android Components
- **Activities**: 12+ activities for different features
- **Services**: Foreground service for usage tracking
- **Broadcast Receivers**: App installation detection
- **RecyclerView**: Efficient list displays
- **Material Design**: Modern UI components

### Key Android APIs
- **UsageStatsManager** - App usage tracking
- **PackageManager** - Installed apps management
- **SharedPreferences** - Local data storage
- **Notifications** - Parent alerts

### Networking
- **Retrofit 2** - REST API client
- **OkHttp** - HTTP client with interceptors
- **Gson** - JSON serialization/deserialization

### UI/UX
- **Material Components** - Cards, buttons, text fields
- **Custom Layouts** - Responsive and adaptive designs
- **Design System** - Consistent colors, spacing, and typography

## 📋 Prerequisites

- Android Studio Arctic Fox or later
- JDK 8 or higher
- Android SDK (API 21+)
- Gradle 7.0+

## 🚀 Installation Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/smart-ai-parental-control.git
cd smart-ai-parental-control
```

### 2. Open in Android Studio
- Launch Android Studio
- Select "Open an Existing Project"
- Navigate to the cloned repository folder
- Click "OK" and wait for Gradle sync

### 3. Configure Dependencies
Add the following dependencies to `app/build.gradle`:

```gradle
dependencies {
    // Material Design
    implementation 'com.google.android.material:material:1.9.0'
    
    // RecyclerView
    implementation 'androidx.recyclerview:recyclerview:1.3.1'
    
    // Retrofit for networking
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    
    // OkHttp
    implementation 'com.squareup.okhttp3:okhttp:4.11.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.11.0'
    
    // Gson
    implementation 'com.google.code.gson:gson:2.10.1'
}
```

### 4. Update API Configuration
Edit `app/src/main/java/com/smartai/parentalcontrol/network/ApiClient.java`:

```java
private static final String BASE_URL = "https://your-backend-api.com/api/";
```

Replace with your actual backend API URL.

### 5. Grant Required Permissions
The app requires the following permissions (already configured in AndroidManifest.xml):
- `INTERNET` - Network communication
- `PACKAGE_USAGE_STATS` - App usage tracking
- `FOREGROUND_SERVICE` - Background monitoring
- `QUERY_ALL_PACKAGES` - Access installed apps
- `SYSTEM_ALERT_WINDOW` - Overlay blocking screens
- `POST_NOTIFICATIONS` - Parent notifications

### 6. Enable Usage Access
On first run, users must grant Usage Access permission:
1. Go to Settings → Apps → Special app access → Usage access
2. Enable permission for Smart AI Parental Control

### 7. Build and Run
```bash
# Build the project
./gradlew build

# Install on connected device/emulator
./gradlew installDebug

# Or use Android Studio's Run button (Shift + F10)
```

## 📱 App Structure

```
app/src/main/java/com/smartai/parentalcontrol/
├── models/              # Data models (User, AppLimit, UsageLog, Request)
├── network/             # API client and services
├── activities/          # UI screens
│   ├── MainActivity
│   ├── LoginActivity
│   ├── RegisterActivity
│   ├── ParentDashboardActivity
│   ├── StudentDashboardActivity
│   ├── LimitManagementActivity
│   ├── RequestsActivity
│   ├── RequestTimeActivity
│   ├── BlockOverlayActivity
│   ├── DeviceLockedActivity
│   └── SettingsActivity
├── adapters/            # RecyclerView adapters
├── services/            # Background services
└── receivers/           # Broadcast receivers
```

## 🎨 Design System

The app uses a consistent design system with:
- **Primary Color**: #2196F3 (Blue)
- **Accent Color**: #FF9800 (Orange)
- **Error Color**: #F44336 (Red)
- **Spacing**: 4dp, 8dp, 16dp, 24dp, 32dp, 48dp
- **Typography**: Display (28sp), Heading (24sp), Title (20sp), Body (14sp)
- **Card Radius**: 12dp
- **Button Height**: 48dp

## 🔐 Security Considerations

- Passwords should be hashed before storage (implement in backend)
- Use HTTPS for all API communications
- Implement token-based authentication
- Store sensitive data securely using Android Keystore
- Validate all user inputs
- Implement rate limiting for API requests

## 🧪 Testing

### Manual Testing
1. Test parent registration and login
2. Test student registration and login
3. Verify app usage tracking
4. Test time limit creation and enforcement
5. Test request submission and approval flow
6. Verify device lock functionality

### Recommended Test Devices
- Android 5.0+ (API 21+)
- Various screen sizes (phone and tablet)
- Different Android versions

## 📝 Configuration

### Backend Setup
You'll need to set up a backend API with the following endpoints:
- `POST /auth/register` - User registration
- `POST /auth/login` - User authentication
- `GET /limits/student/{id}` - Get app limits
- `POST /limits` - Create app limit
- `GET /requests/parent/{id}` - Get pending requests
- `POST /usage` - Log usage data

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Authors

- Your Name - Initial work

## 🙏 Acknowledgments

- Material Design guidelines by Google
- Android developer documentation
- Open source community

## 📞 Support

For support, email support@smartaiparentalcontrol.com or open an issue in the repository.

## 🗺️ Roadmap

- [ ] AI-powered usage recommendations
- [ ] Multi-device support
- [ ] Geofencing features
- [ ] Content filtering
- [ ] Screen time scheduling
- [ ] Family sharing features
- [ ] iOS version

## 📊 Screenshots

_Add screenshots of your app here_

## 🔄 Version History

- **v1.0.0** (Current)
  - Initial release
  - Basic parental control features
  - App usage tracking
  - Time limit management
  - Request system
  - Device lock functionality

---

Made with ❤️ for safer digital parenting
