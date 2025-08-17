package com.grayseal.notesapp.util

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

object HapticFeedback {
    
    // Different vibration patterns for different interactions
    enum class VibrationType {
        LIGHT,      // Light tap feedback
        MEDIUM,     // Medium feedback for important actions
        HEAVY,      // Heavy feedback for critical actions
        SUCCESS,    // Success pattern
        ERROR,      // Error pattern
        CLICK       // Simple click feedback
    }
    
    fun vibrate(context: Context, type: VibrationType) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        
        if (!vibrator.hasVibrator()) return
        
        val effect = when (type) {
            VibrationType.LIGHT -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    VibrationEffect.createOneShot(20, VibrationEffect.DEFAULT_AMPLITUDE)
                } else {
                    @Suppress("DEPRECATION")
                    VibrationEffect.createOneShot(20, 50)
                }
            }
            VibrationType.MEDIUM -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
                } else {
                    @Suppress("DEPRECATION")
                    VibrationEffect.createOneShot(50, 100)
                }
            }
            VibrationType.HEAVY -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
                } else {
                    @Suppress("DEPRECATION")
                    VibrationEffect.createOneShot(100, 150)
                }
            }
            VibrationType.SUCCESS -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    VibrationEffect.createWaveform(
                        longArrayOf(0, 50, 100, 50),
                        intArrayOf(0, 100, 0, 100),
                        -1
                    )
                } else {
                    @Suppress("DEPRECATION")
                    VibrationEffect.createWaveform(
                        longArrayOf(0, 50, 100, 50),
                        intArrayOf(0, 100, 0, 100),
                        -1
                    )
                }
            }
            VibrationType.ERROR -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    VibrationEffect.createWaveform(
                        longArrayOf(0, 100, 50, 100),
                        intArrayOf(0, 150, 0, 150),
                        -1
                    )
                } else {
                    @Suppress("DEPRECATION")
                    VibrationEffect.createWaveform(
                        longArrayOf(0, 100, 50, 100),
                        intArrayOf(0, 150, 0, 150),
                        -1
                    )
                }
            }
            VibrationType.CLICK -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE)
                } else {
                    @Suppress("DEPRECATION")
                    VibrationEffect.createOneShot(10, 30)
                }
            }
        }
        
        vibrator.vibrate(effect)
    }
    
    // Convenience methods for common interactions
    fun lightTap(context: Context) = vibrate(context, VibrationType.LIGHT)
    fun mediumTap(context: Context) = vibrate(context, VibrationType.MEDIUM)
    fun heavyTap(context: Context) = vibrate(context, VibrationType.HEAVY)
    fun success(context: Context) = vibrate(context, VibrationType.SUCCESS)
    fun error(context: Context) = vibrate(context, VibrationType.ERROR)
    fun click(context: Context) = vibrate(context, VibrationType.CLICK)
}
