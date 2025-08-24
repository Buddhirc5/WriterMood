# WriterMood App 📝✨

[![Android CI](https://github.com/buddhisandeepa/writermood/actions/workflows/android.yml/badge.svg)](https://github.com/buddhisandeepa/writermood/actions/workflows/android.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.8+-blue.svg)](https://kotlinlang.org/)
[![Compose](https://img.shields.io/badge/Compose-1.3+-orange.svg)](https://developer.android.com/jetpack/compose)
[![API](https://img.shields.io/badge/API-26+-green.svg)](https://developer.android.com/about/versions/oreo)

A sophisticated note-taking application built with modern Android development practices, featuring intelligent mood detection and analytics. WriterMood combines the power of Jetpack Compose with advanced sentiment analysis to help users track their emotional journey through writing.

[![WriterMood Demo](https://img.shields.io/badge/Demo-Watch%20Demo-red.svg)](https://github.com/buddhisandeepa/writermood#demo)
[![Download APK](https://img.shields.io/badge/Download-APK-brightgreen.svg)](https://github.com/buddhisandeepa/writermood/releases/latest)

## 📋 Table of Contents

- [Features](#-features)
- [Demo](#-demo)
- [Quick Start](#-quick-start)
- [Architecture](#️-architecture)
- [Screens & Components](#-screens--components)
- [Data Layer](#️-data-layer)
- [Mood Detection System](#-mood-detection-system)
- [UI/UX Components](#-uiux-components)
- [Technical Stack](#-technical-stack)
- [Performance Optimizations](#-performance-optimizations)
- [Security & Privacy](#-security--privacy)
- [Analytics & Insights](#-analytics--insights)
- [Development Setup](#️-development-setup)
- [Testing](#-testing)
- [Future Enhancements](#-future-enhancements)
- [Contributing](#-contributing)
- [License](#-license)
- [Project Stats](#-project-stats)
- [Support](#-support)

## 🚀 Features

### Core Functionality
- **Smart Note Creation**: Create, edit, and delete notes with automatic mood detection
- **Mood Analytics Dashboard**: Visualize your emotional patterns over time
- **Theme Customization**: Multiple themes with dynamic color schemes
- **Haptic Feedback**: Enhanced user experience with tactile responses
- **Performance Optimized**: Built for smooth performance on all devices

### Advanced Features
- **AI-Powered Mood Detection**: Automatic sentiment analysis using Hugging Face API
- **Mood Tracking**: Categorize notes by emotional states (happy, sad, angry, love, fear, surprise, neutral)
- **Writing Analytics**: Word count tracking and writing insights
- **Responsive Design**: Adaptive UI that works across different screen sizes
- **Data Persistence**: Secure local storage with Room database

## 🏗️ Architecture

WriterMood follows the **MVVM (Model-View-ViewModel)** architecture pattern with **Clean Architecture** principles:

```
┌─────────────────────────────────────────────────────────────┐
│                        UI Layer                              │
├─────────────────────────────────────────────────────────────┤
│  Screens (Compose) │ Components │ Theme │ Navigation        │
├─────────────────────────────────────────────────────────────┤
│                    ViewModel Layer                          │
├─────────────────────────────────────────────────────────────┤
│                    Repository Layer                         │
├─────────────────────────────────────────────────────────────┤
│                    Data Layer                               │
├─────────────────────────────────────────────────────────────┤
│  Room Database │ API Services │ Preferences │ Utilities     │
└─────────────────────────────────────────────────────────────┘
```

## 📱 Screens & Components

### 1. **Splash Screen** (`SplashScreen.kt`)
- Animated welcome screen with app branding
- Smooth transition to main application
- Performance optimization during app startup

### 2. **Welcome Screen** (`WelcomeScreen.kt`)
- User onboarding experience
- App introduction and feature highlights
- Navigation to main functionality

### 3. **Home Screen** (`HomeScreen.kt`)
- **Note List Display**: Shows all saved notes with mood indicators
- **Search & Filter**: Find notes by content or mood
- **Quick Actions**: Add new note, delete, edit functionality
- **Mood Visualization**: Color-coded mood indicators for each note
- **Pull-to-Refresh**: Update note list with latest data

### 4. **Note Screen** (`NoteScreen.kt`)
- **Rich Text Editor**: Create and edit notes with formatting
- **Real-time Mood Detection**: Live sentiment analysis as you type
- **Auto-save**: Automatic saving of note content
- **Mood Selection**: Manual mood override if needed
- **Word Count**: Real-time writing statistics

### 5. **Mood Dashboard** (`MoodDashboardScreen.kt`)
- **Mood Analytics**: Visual charts showing emotional patterns
- **Mood Distribution**: Pie charts and bar graphs
- **Time-based Analysis**: Mood trends over time
- **Writing Insights**: Statistics about writing habits
- **Mood Filtering**: View notes by specific emotions

### 6. **Theme Changer** (`ThemeChangerScreen.kt`)
- **Multiple Themes**: Light, Dark, and custom color schemes
- **Dynamic Colors**: Adaptive color palettes
- **Theme Persistence**: Remember user preferences
- **Preview Mode**: See theme changes in real-time

## 🗄️ Data Layer

### Database Architecture
- **Room Database**: Local SQLite database with Room ORM
- **Entity**: `Note` model with UUID primary key
- **DAO**: Data Access Object for CRUD operations
- **Migrations**: Database schema versioning

### Note Entity Structure
```kotlin
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "note_title") val title: String,
    @ColumnInfo(name = "note_description") val note: String,
    @ColumnInfo(name = "note_entry_date") val entry_date: String,
    @ColumnInfo(name = "note_mood") val mood: String = "neutral"
)
```

### Repository Pattern
- **NoteRepository**: Central data access layer
- **Mood Detection Integration**: Automatic sentiment analysis
- **Coroutines Support**: Asynchronous data operations
- **Flow Integration**: Reactive data streams

## 🧠 Mood Detection System

### AI-Powered Analysis
- **Hugging Face API**: Integration with state-of-the-art sentiment analysis
- **Fallback System**: Rule-based detection when API unavailable
- **Mood Categories**: 7 emotional states (happy, sad, angry, love, fear, surprise, neutral)
- **Real-time Processing**: Instant mood detection as user types

### Sentiment Analysis Features
- **Text Processing**: Advanced NLP for accurate mood detection
- **Context Awareness**: Understands writing context and tone
- **Confidence Scoring**: Provides confidence levels for mood predictions
- **Custom Keywords**: Extensible keyword-based detection

## 🎨 UI/UX Components

### Theme System
- **Material Design 3**: Latest Material Design principles
- **Dynamic Colors**: Adaptive color schemes
- **Typography**: Custom font families and text styles
- **Shape System**: Consistent corner radius and elevation

### Custom Components
- **MoodCharts**: Interactive charts for mood visualization
- **WordCount**: Real-time writing statistics display
- **HapticFeedback**: Tactile response system
- **PerformanceOptimizer**: UI performance monitoring

## 🔧 Technical Stack

### Core Technologies
- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern declarative UI toolkit
- **Room Database**: Local data persistence
- **Hilt**: Dependency injection framework
- **Coroutines**: Asynchronous programming
- **Flow**: Reactive streams

### Libraries & Dependencies
```gradle
// UI & Navigation
implementation "androidx.compose.ui:ui:$compose_ui_version"
implementation "androidx.navigation:navigation-compose:2.5.3"
implementation "androidx.compose.material3:material3:1.0.1"

// Architecture Components
implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"
implementation "androidx.room:room-ktx:$room_version"

// Dependency Injection
implementation "com.google.dagger:hilt-android:2.44"

// Networking
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

// Data Storage
implementation "androidx.datastore:datastore-preferences:1.0.0"
```

## 🚀 Performance Optimizations

### Memory Management
- **Lazy Loading**: Efficient list rendering with Compose LazyColumn
- **Image Optimization**: Compressed assets and efficient loading
- **Database Optimization**: Indexed queries and efficient data access

### UI Performance
- **Compose Optimization**: Minimized recompositions
- **Background Processing**: Heavy operations on background threads
- **Caching**: Smart caching strategies for better performance

### Battery Optimization
- **Efficient API Calls**: Minimal network requests
- **Background Work**: Optimized background processing
- **Resource Management**: Proper resource cleanup

## 🔐 Security & Privacy

### Data Protection
- **Local Storage**: All data stored locally on device
- **No Cloud Sync**: Privacy-first approach
- **Secure Permissions**: Minimal required permissions
- **Data Encryption**: Room database encryption support

### API Security
- **Secure API Keys**: Environment-based configuration
- **Request Validation**: Input sanitization and validation
- **Error Handling**: Graceful error management

## 📊 Analytics & Insights

### Writing Analytics
- **Word Count Tracking**: Real-time writing statistics
- **Mood Patterns**: Emotional journey visualization
- **Writing Frequency**: Usage patterns and trends
- **Performance Metrics**: App performance monitoring

### User Experience Metrics
- **Screen Time**: Time spent on different features
- **Feature Usage**: Most used functionality tracking
- **Error Tracking**: Crash and error monitoring
- **Performance Monitoring**: App responsiveness metrics

## 🛠️ Development Setup

### Prerequisites
- Android Studio Arctic Fox or later
- Kotlin 1.8+
- Android SDK 33+
- Minimum API Level: 26 (Android 8.0)

### Installation
1. Clone the repository
2. Open project in Android Studio
3. Sync Gradle files
4. Configure API keys (if using external services)
5. Build and run the application

### Build Configuration
```gradle
android {
    compileSdk 33
    minSdk 26
    targetSdk 33
    
    buildTypes {
        release {
            minifyEnabled true
            shrinkResources true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt')
        }
    }
}
```

## 🧪 Testing

### Test Coverage
- **Unit Tests**: ViewModel and Repository testing
- **UI Tests**: Compose UI component testing
- **Integration Tests**: Database and API integration
- **Performance Tests**: App performance benchmarking

### Testing Tools
- **JUnit**: Unit testing framework
- **Espresso**: UI testing
- **Compose Testing**: Compose-specific testing utilities
- **Mockito**: Mocking framework

## 📈 Future Enhancements

### Planned Features
- **Cloud Sync**: Optional cloud backup and sync
- **Advanced Analytics**: Machine learning insights
- **Collaboration**: Shared notes and mood tracking
- **Export Options**: PDF and text export functionality
- **Voice Notes**: Speech-to-text integration
- **Mood Reminders**: Smart notification system

### Technical Improvements
- **Offline AI**: Local sentiment analysis
- **Performance**: Further optimization for low-end devices
- **Accessibility**: Enhanced accessibility features
- **Internationalization**: Multi-language support

## 📱 Demo

![WriterMood Demo](images/demo.gif)

*Note: Demo GIF will be added once the app is fully functional*

## 🚀 Quick Start

### Download
- **Latest Release**: [Download APK](https://github.com/buddhisandeepa/writermood/releases/latest)
- **Direct APK**: [app-debug.apk](app/build/outputs/apk/debug/app-debug.apk)

### Installation
1. Enable "Install from Unknown Sources" in your Android settings
2. Download the APK file
3. Install and enjoy WriterMood!

## 📁 Repository Structure

```
writermood/
├── app/
│   ├── src/main/java/com/grayseal/notesapp/
│   │   ├── data/           # Database, API services, preferences
│   │   ├── di/             # Dependency injection modules
│   │   ├── model/          # Data models and entities
│   │   ├── navigation/     # Navigation components
│   │   ├── repository/     # Repository layer
│   │   ├── screens/        # UI screens and ViewModels
│   │   ├── ui/             # UI components and theme
│   │   ├── util/           # Utility classes
│   │   ├── MainActivity.kt # Main activity
│   │   └── NoteApplication.kt # Application class
│   ├── src/main/res/       # Resources (layouts, drawables, etc.)
│   └── build.gradle        # App-level build configuration
├── .github/                # GitHub-specific files
│   ├── ISSUE_TEMPLATE/     # Issue templates
│   ├── workflows/          # GitHub Actions
│   ├── CONTRIBUTING.md     # Contributing guidelines
│   ├── CODE_OF_CONDUCT.md  # Code of conduct
│   └── FUNDING.yml         # Funding configuration
├── gradle/                 # Gradle wrapper
├── build.gradle           # Project-level build configuration
├── README.md              # This file
└── LICENSE                # MIT License
```

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guidelines](/.github/CONTRIBUTING.md) for details on:
- Code style and standards
- Pull request process
- Issue reporting
- Development setup

### How to Contribute
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Issue Templates
- 🐛 [Bug Report](/.github/ISSUE_TEMPLATE/bug_report.md)
- 💡 [Feature Request](/.github/ISSUE_TEMPLATE/feature_request.md)

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📊 Project Stats

![GitHub stars](https://img.shields.io/github/stars/buddhisandeepa/writermood?style=social)
![GitHub forks](https://img.shields.io/github/forks/buddhisandeepa/writermood?style=social)
![GitHub issues](https://img.shields.io/github/issues/buddhisandeepa/writermood)
![GitHub pull requests](https://img.shields.io/github/issues-pr/buddhisandeepa/writermood)
![GitHub contributors](https://img.shields.io/github/contributors/buddhisandeepa/writermood)
![GitHub last commit](https://img.shields.io/github/last-commit/buddhisandeepa/writermood)

## 🌟 Star History

[![Star History Chart](https://api.star-history.com/svg?repos=buddhisandeepa/writermood&type=Date)](https://star-history.com/#buddhisandeepa/writermood&Date)

## 🙏 Acknowledgments

- **Hugging Face**: For sentiment analysis API
- **Jetpack Compose**: For modern UI development
- **Material Design**: For design system inspiration
- **Android Community**: For continuous support and feedback

## 📞 Support

- **Issues**: [GitHub Issues](https://github.com/buddhisandeepa/writermood/issues)
- **Discussions**: [GitHub Discussions](https://github.com/buddhisandeepa/writermood/discussions)
- **Email**: [Contact Developer](mailto:your-email@example.com)

## 💰 Support the Project

If you find WriterMood helpful, please consider supporting the project:

[![GitHub Sponsors](https://img.shields.io/badge/GitHub%20Sponsors-Support%20Me-red.svg)](https://github.com/sponsors/buddhisandeepa)
[![Buy Me a Coffee](https://img.shields.io/badge/Buy%20Me%20a%20Coffee-Support%20Me-yellow.svg)](https://buymeacoffee.com/buddhisandeepa)

---

**WriterMood** - Where every word tells a story, and every story reveals a mood. 📝✨

<div align="center">
  <sub>Built with ❤️ by <a href="https://github.com/buddhisandeepa">Buddhi Sandeepa</a></sub>
</div>