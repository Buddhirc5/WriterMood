package com.grayseal.notesapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ImageUtils {
    private const val CACHE_SIZE = 20 // Number of images to cache
    
    // Memory cache for images
    private val imageCache = object : LruCache<String, Bitmap>(CACHE_SIZE) {
        override fun sizeOf(key: String, bitmap: Bitmap): Int {
            return bitmap.byteCount / 1024 // Size in KB
        }
    }
    
    /**
     * Load and cache image efficiently
     */
    suspend fun loadImage(context: Context, resourceId: Int): Bitmap? {
        return withContext(Dispatchers.IO) {
            val key = resourceId.toString()
            
            // Check cache first
            imageCache.get(key)?.let { return@withContext it }
            
            // Load from resources if not in cache
            try {
                val options = BitmapFactory.Options().apply {
                    inSampleSize = 2 // Reduce memory usage
                    inPreferredConfig = Bitmap.Config.RGB_565 // Use less memory
                }
                
                val bitmap = BitmapFactory.decodeResource(context.resources, resourceId, options)
                bitmap?.let { imageCache.put(key, it) }
                bitmap
            } catch (e: Exception) {
                null
            }
        }
    }
    
    /**
     * Clear image cache
     */
    fun clearCache() {
        imageCache.evictAll()
    }
    
    /**
     * Get cache size info
     */
    fun getCacheInfo(): String {
        return "Cache size: ${imageCache.size()}/$CACHE_SIZE"
    }
}
