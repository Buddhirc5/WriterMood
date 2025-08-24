package com.grayseal.notesapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PerformanceOptimizer {
    
    // Memory management for low-end devices
    private const val MAX_MEMORY_CACHE_SIZE = 8 * 1024 * 1024 // 8MB for 2GB devices
    private const val MAX_IMAGE_CACHE_SIZE = 4 // Limit image cache for low memory
    
    // Image cache with reduced size for low-end devices
    private val imageCache = object : LruCache<String, Bitmap>(MAX_IMAGE_CACHE_SIZE) {
        override fun sizeOf(key: String, bitmap: Bitmap): Int {
            return bitmap.byteCount / 1024 // Size in KB
        }
    }
    
    // Memory monitoring
    fun getAvailableMemory(context: Context): Long {
        val runtime = Runtime.getRuntime()
        return runtime.maxMemory() - runtime.totalMemory() + runtime.freeMemory()
    }
    
    fun isLowMemoryDevice(context: Context): Boolean {
        return getAvailableMemory(context) < 500 * 1024 * 1024 // Less than 500MB available
    }
    
    // Optimized image loading for low-end devices
    suspend fun loadOptimizedImage(context: Context, resourceId: Int): Bitmap? = withContext(Dispatchers.IO) {
        try {
            // Check if already in cache
            val cacheKey = resourceId.toString()
            imageCache.get(cacheKey)?.let { return@withContext it }
            
            // Load with reduced quality for low-end devices
            val options = BitmapFactory.Options().apply {
                inSampleSize = if (isLowMemoryDevice(context)) 4 else 2 // Higher sample size for low memory
                inPreferredConfig = Bitmap.Config.RGB_565 // Use less memory than ARGB_8888
                inPurgeable = true // Allow system to purge when needed
                inInputShareable = true
            }
            
            val bitmap = BitmapFactory.decodeResource(context.resources, resourceId, options)
            
            // Cache the bitmap
            bitmap?.let { imageCache.put(cacheKey, it) }
            
            bitmap
        } catch (e: Exception) {
            null
        }
    }
    
    // Clear caches when memory is low
    fun clearCaches() {
        imageCache.evictAll()
        System.gc() // Request garbage collection
    }
    
    // Memory usage monitoring
    fun getMemoryUsage(): String {
        val runtime = Runtime.getRuntime()
        val usedMemory = runtime.totalMemory() - runtime.freeMemory()
        val maxMemory = runtime.maxMemory()
        val availableMemory = maxMemory - usedMemory
        
        return "Used: ${usedMemory / 1024 / 1024}MB, " +
               "Available: ${availableMemory / 1024 / 1024}MB, " +
               "Max: ${maxMemory / 1024 / 1024}MB"
    }
    
    // Optimize for low-end devices
    fun optimizeForLowEndDevice() {
        // Reduce cache sizes
        imageCache.trimToSize(MAX_IMAGE_CACHE_SIZE / 2)
        
        // Request garbage collection
        System.gc()
    }
}
