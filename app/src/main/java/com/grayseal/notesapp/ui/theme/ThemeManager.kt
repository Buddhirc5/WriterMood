package com.grayseal.notesapp.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

enum class AppTheme {
    PINK, BLUE, GREEN, PURPLE, ORANGE
}

object ThemeManager {
    var currentTheme by mutableStateOf(AppTheme.PINK)
        private set
    
    fun setTheme(theme: AppTheme) {
        currentTheme = theme
    }
    
    fun getPrimaryColor(): Color {
        return when (currentTheme) {
            AppTheme.PINK -> Color(0xFFdaaac0)
            AppTheme.BLUE -> Color(0xFF4A90E2)
            AppTheme.GREEN -> Color(0xFF4CAF50)
            AppTheme.PURPLE -> Color(0xFF9C27B0)
            AppTheme.ORANGE -> Color(0xFFFF9800)
        }
    }
    
    fun getSecondaryColor(): Color {
        return when (currentTheme) {
            AppTheme.PINK -> Color(0xFFdeb7c5)
            AppTheme.BLUE -> Color(0xFF81C784)
            AppTheme.GREEN -> Color(0xFF8BC34A)
            AppTheme.PURPLE -> Color(0xFFE1BEE7)
            AppTheme.ORANGE -> Color(0xFFFFCC02)
        }
    }
    
    fun getAccentColor(): Color {
        return when (currentTheme) {
            AppTheme.PINK -> Color(0xFFaf7184)
            AppTheme.BLUE -> Color(0xFF1976D2)
            AppTheme.GREEN -> Color(0xFF388E3C)
            AppTheme.PURPLE -> Color(0xFF7B1FA2)
            AppTheme.ORANGE -> Color(0xFFF57C00)
        }
    }
    
    fun getBackgroundColor(): Color {
        return when (currentTheme) {
            AppTheme.PINK -> Color(0xFFf8f4ff)
            AppTheme.BLUE -> Color(0xFFf0f8ff)
            AppTheme.GREEN -> Color(0xFFf1f8e9)
            AppTheme.PURPLE -> Color(0xFFf3e5f5)
            AppTheme.ORANGE -> Color(0xFFfff3e0)
        }
    }
    
    fun getBorderColor(): Color {
        return when (currentTheme) {
            AppTheme.PINK -> Color(0xFFefcd95)
            AppTheme.BLUE -> Color(0xFFBBDEFB)
            AppTheme.GREEN -> Color(0xFFC8E6C9)
            AppTheme.PURPLE -> Color(0xFFE1BEE7)
            AppTheme.ORANGE -> Color(0xFFFFE0B2)
        }
    }
    
    fun getTextColor(): Color {
        return when (currentTheme) {
            AppTheme.PINK -> Color(0xFF92a4a2)
            AppTheme.BLUE -> Color(0xFF546E7A)
            AppTheme.GREEN -> Color(0xFF4A5D23)
            AppTheme.PURPLE -> Color(0xFF6A1B9A)
            AppTheme.ORANGE -> Color(0xFFE65100)
        }
    }
}
