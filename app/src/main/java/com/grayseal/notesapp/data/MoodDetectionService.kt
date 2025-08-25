package com.grayseal.notesapp.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Inject

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

class MoodDetectionService @Inject constructor(
    private val apiKey: String
) {
    private val api: HuggingFaceApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-inference.huggingface.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(HuggingFaceApi::class.java)
    }

    suspend fun detectMood(text: String): String? {
        return try {
            val response = api.analyzeSentiment("Bearer $apiKey", SentimentRequest(inputs = text))
            if (response.isSuccessful) {
                val body = response.body()
                body?.maxByOrNull { it.score }?.label?.lowercase()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        fun fallbackMood(text: String): String {
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
