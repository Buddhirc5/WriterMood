package com.grayseal.notesapp.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface HuggingFaceApi {
    @POST("https://api-inference.huggingface.co/models/cardiffnlp/twitter-roberta-base-sentiment-latest")
    suspend fun analyzeSentiment(
        @Header("Authorization") authorization: String,
        @Body request: SentimentRequest
    ): Response<List<SentimentResponse>>
}

data class SentimentRequest(
    val inputs: String
)

data class SentimentResponse(
    val label: String,
    val score: Double
)

class MoodDetectionService {
    companion object {
        private const val API_URL = "https://api-inference.huggingface.co/models/cardiffnlp/twitter-roberta-base-sentiment-latest"
        private const val API_KEY = "hf_demo" // This is a demo key, in production use a real API key
        
        fun detectMood(text: String): String {
            // Simple rule-based mood detection as fallback
            val lowerText = text.lowercase()
            return when {
                lowerText.contains("happy") || lowerText.contains("joy") || lowerText.contains("excited") || 
                lowerText.contains("great") || lowerText.contains("wonderful") || lowerText.contains("amazing") -> "happy"
                lowerText.contains("sad") || lowerText.contains("depressed") || lowerText.contains("unhappy") || 
                lowerText.contains("terrible") || lowerText.contains("awful") -> "sad"
                lowerText.contains("angry") || lowerText.contains("mad") || lowerText.contains("furious") || 
                lowerText.contains("hate") || lowerText.contains("annoyed") -> "angry"
                lowerText.contains("love") || lowerText.contains("adore") || lowerText.contains("passion") -> "love"
                lowerText.contains("fear") || lowerText.contains("scared") || lowerText.contains("afraid") -> "fear"
                lowerText.contains("surprise") || lowerText.contains("shocked") || lowerText.contains("wow") -> "surprise"
                else -> "neutral"
            }
        }
    }
}
