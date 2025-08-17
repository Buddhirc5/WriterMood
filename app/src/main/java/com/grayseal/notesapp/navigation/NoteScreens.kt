package com.grayseal.notesapp.navigation

enum class NoteScreens {
    SplashScreen,
    WelcomeScreen,
    HomeScreen,
    NoteScreen,
    MoodDashboardScreen,
    ThemeChangerScreen;

    companion object {
        fun fromRoute(route: String?): NoteScreens = when (route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            WelcomeScreen.name -> WelcomeScreen
            HomeScreen.name -> HomeScreen
            NoteScreen.name -> NoteScreen
            MoodDashboardScreen.name -> MoodDashboardScreen
            ThemeChangerScreen.name -> ThemeChangerScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}