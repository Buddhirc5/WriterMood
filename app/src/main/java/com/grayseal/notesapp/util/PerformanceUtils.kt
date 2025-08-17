package com.grayseal.notesapp.util

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object PerformanceUtils {
    private const val TAG = "PerformanceUtils"
    
    /**
     * Execute heavy operations on background thread
     */
    suspend fun <T> executeOnBackground(operation: suspend () -> T): T {
        return withContext(Dispatchers.IO) {
            operation()
        }
    }
    
    /**
     * Execute operations with performance logging
     */
    suspend fun <T> executeWithLogging(operationName: String, operation: suspend () -> T): T {
        val startTime = System.currentTimeMillis()
        try {
            val result = operation()
            val duration = System.currentTimeMillis() - startTime
            Log.d(TAG, "$operationName completed in ${duration}ms")
            return result
        } catch (e: Exception) {
            val duration = System.currentTimeMillis() - startTime
            Log.e(TAG, "$operationName failed after ${duration}ms", e)
            throw e
        }
    }
    
    /**
     * Debounce function calls to prevent excessive executions
     */
    fun debounce(
        delayMillis: Long = 300L,
        scope: CoroutineScope,
        action: () -> Unit
    ): () -> Unit {
        var lastExecutionTime = 0L
        return {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastExecutionTime > delayMillis) {
                lastExecutionTime = currentTime
                scope.launch(Dispatchers.Main) {
                    action()
                }
            }
        }
    }
    
    /**
     * Throttle function calls to limit execution frequency
     */
    fun throttle(
        delayMillis: Long = 100L,
        scope: CoroutineScope,
        action: () -> Unit
    ): () -> Unit {
        var lastExecutionTime = 0L
        return {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastExecutionTime >= delayMillis) {
                lastExecutionTime = currentTime
                scope.launch(Dispatchers.Main) {
                    action()
                }
            }
        }
    }
}
