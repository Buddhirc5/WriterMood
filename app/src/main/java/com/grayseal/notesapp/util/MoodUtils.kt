package com.grayseal.notesapp.util

import androidx.compose.ui.graphics.Color

object MoodUtils {
    fun getMoodColor(mood: String): Color {
        return when (mood.lowercase()) {
            "happy" -> Color(0xFF4CAF50)
            "sad" -> Color(0xFF2196F3)
            "angry" -> Color(0xFFF44336)
            "love" -> Color(0xFFE91E63)
            "fear" -> Color(0xFF9C27B0)
            "surprise" -> Color(0xFFFF9800)
            else -> Color(0xFF9E9E9E) // neutral
        }
    }

    fun String.capitalize(): String {
        return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}
