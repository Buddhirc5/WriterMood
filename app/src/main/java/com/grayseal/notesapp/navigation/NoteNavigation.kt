package com.grayseal.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grayseal.notesapp.screens.*
import com.grayseal.notesapp.util.getCurrentDate
import com.grayseal.notesapp.model.Note

@Composable
fun NoteNavigation() {
    val navController = rememberNavController()
    val noteViewModel = viewModel<NoteViewModel>()
    NavHost(navController = navController, startDestination = NoteScreens.SplashScreen.name) {
        composable(NoteScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        composable(NoteScreens.WelcomeScreen.name) {
            // Pass where this should lead user to
            WelcomeScreen(navController = navController)
        }
        composable(NoteScreens.HomeScreen.name) {
            HomeScreen(navController = navController, noteViewModel)
        }
        composable(NoteScreens.NoteScreen.name) {
            NoteScreen(navController = navController, noteViewModel)
        }
        composable(
            route = "edit_note/{noteId}/{title}/{content}/{mood}",
            arguments = listOf(
                androidx.navigation.navArgument("noteId") { type = androidx.navigation.NavType.StringType },
                androidx.navigation.navArgument("title") { type = androidx.navigation.NavType.StringType },
                androidx.navigation.navArgument("content") { type = androidx.navigation.NavType.StringType },
                androidx.navigation.navArgument("mood") { type = androidx.navigation.NavType.StringType }
            )
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId") ?: ""
            val title = java.net.URLDecoder.decode(backStackEntry.arguments?.getString("title") ?: "", "UTF-8")
            val content = java.net.URLDecoder.decode(backStackEntry.arguments?.getString("content") ?: "", "UTF-8")
            val mood = java.net.URLDecoder.decode(backStackEntry.arguments?.getString("mood") ?: "neutral", "UTF-8")
            
            val noteToEdit = Note(
                id = java.util.UUID.fromString(noteId),
                title = title,
                note = content,
                mood = mood
            )
            NoteScreen(
                navController = navController, 
                noteViewModel = noteViewModel,
                editMode = true,
                noteToEdit = noteToEdit
            )
        }
        composable(NoteScreens.MoodDashboardScreen.name) {
            MoodDashboardScreen(
                viewModel = noteViewModel,
                onNavigateToHome = { navController.navigate(NoteScreens.HomeScreen.name) }
            )
        }
        composable(NoteScreens.ThemeChangerScreen.name) {
            ThemeChangerScreen(navController = navController)
        }

    }
}