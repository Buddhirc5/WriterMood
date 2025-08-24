package com.grayseal.notesapp.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grayseal.notesapp.data.MoodCount
import com.grayseal.notesapp.model.Note
import com.grayseal.notesapp.ui.theme.sonoFamily
import com.grayseal.notesapp.util.MoodUtils.getMoodColor
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MoodTrendChart(
    notes: List<Note>,
    modifier: Modifier = Modifier
) {
    val recentNotes = notes.takeLast(7) // Last 7 notes for trend
    val animatedProgress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1500, easing = FastOutSlowInEasing)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Mood Trend (Last 7 Notes)",
                fontSize = 18.sp,
                fontFamily = sonoFamily,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (recentNotes.isNotEmpty()) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                ) {
                    val width = size.width
                    val height = size.height
                    val pointCount = recentNotes.size
                    
                    if (pointCount > 1) {
                        val points = recentNotes.mapIndexed { index, note ->
                            val x = (index.toFloat() / (pointCount - 1)) * width
                            val moodValue = getMoodValue(note.mood)
                            val y = height - (moodValue * height * animatedProgress)
                            Offset(x, y)
                        }
                        
                        // Draw trend line
                        val path = Path()
                        path.moveTo(points.first().x, points.first().y)
                        points.drop(1).forEach { point ->
                            path.lineTo(point.x, point.y)
                        }
                        
                        drawPath(
                            path = path,
                            color = Color(0xFF6200EE),
                            style = Stroke(width = 4f, cap = StrokeCap.Round, join = StrokeJoin.Round)
                        )
                        
                        // Draw gradient fill
                        val fillPath = Path()
                        fillPath.moveTo(points.first().x, points.first().y)
                        points.drop(1).forEach { point ->
                            fillPath.lineTo(point.x, point.y)
                        }
                        fillPath.lineTo(points.last().x, height)
                        fillPath.lineTo(points.first().x, height)
                        fillPath.close()
                        
                        val gradient = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF6200EE).copy(alpha = 0.3f),
                                Color(0xFF6200EE).copy(alpha = 0.1f)
                            )
                        )
                        drawPath(path = fillPath, brush = gradient)
                        
                        // Draw points
                        points.forEach { point ->
                            drawCircle(
                                color = getMoodColor(getMoodFromValue(point.y / height)),
                                radius = 8f,
                                center = point
                            )
                            drawCircle(
                                color = Color.White,
                                radius = 4f,
                                center = point
                            )
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No mood data yet",
                        fontSize = 14.sp,
                        fontFamily = sonoFamily,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun MoodPieChart(
    moodStats: List<MoodCount>,
    modifier: Modifier = Modifier
) {
    val totalNotes = moodStats.sumOf { it.count }
    val animatedProgress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Mood Distribution",
                fontSize = 18.sp,
                fontFamily = sonoFamily,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (moodStats.isNotEmpty() && totalNotes > 0) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Pie Chart
                    Canvas(
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        val center = Offset(size.width / 2, size.height / 2)
                        val radius = minOf(size.width, size.height) / 2 - 10f
                        
                        var startAngle = 0f
                        moodStats.forEach { moodCount ->
                            val sweepAngle = (moodCount.count.toFloat() / totalNotes) * 360f * animatedProgress
                            
                            drawArc(
                                color = getMoodColor(moodCount.mood),
                                startAngle = startAngle,
                                sweepAngle = sweepAngle,
                                useCenter = true,
                                topLeft = Offset(center.x - radius, center.y - radius),
                                size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
                            )
                            
                            startAngle += sweepAngle
                        }
                        
                        // Center circle
                        drawCircle(
                            color = Color.White,
                            radius = radius * 0.3f,
                            center = center
                        )
                    }
                    
                    // Legend
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        moodStats.forEach { moodCount ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(getMoodColor(moodCount.mood))
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${moodCount.mood.capitalize()} (${moodCount.count})",
                                    fontSize = 12.sp,
                                    fontFamily = sonoFamily,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No mood data yet",
                        fontSize = 14.sp,
                        fontFamily = sonoFamily,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun MoodBarChart(
    moodStats: List<MoodCount>,
    modifier: Modifier = Modifier
) {
    val maxCount = moodStats.maxOfOrNull { it.count } ?: 1
    val animatedProgress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = FastOutSlowInEasing)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Mood Frequency",
                fontSize = 18.sp,
                fontFamily = sonoFamily,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (moodStats.isNotEmpty()) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                ) {
                    val barWidth = size.width / moodStats.size - 8f
                    val maxBarHeight = size.height - 20f
                    
                    moodStats.forEachIndexed { index, moodCount ->
                        val barHeight = (moodCount.count.toFloat() / maxCount) * maxBarHeight * animatedProgress
                        val x = index * (barWidth + 8f) + 4f
                        val y = size.height - barHeight - 10f
                        
                        // Draw bar
                        drawRoundRect(
                            color = getMoodColor(moodCount.mood),
                            topLeft = Offset(x, y),
                            size = androidx.compose.ui.geometry.Size(barWidth, barHeight),
                            cornerRadius = CornerRadius(4f, 4f)
                        )
                        
                        // Draw value text
                        drawIntoCanvas { canvas ->
                            val paint = android.graphics.Paint().apply {
                                color = android.graphics.Color.WHITE
                                textAlign = android.graphics.Paint.Align.CENTER
                                textSize = 12f
                            }
                            canvas.nativeCanvas.drawText(
                                moodCount.count.toString(),
                                x + barWidth / 2,
                                y + barHeight / 2 + 4f,
                                paint
                            )
                        }
                    }
                }
                
                // X-axis labels
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    moodStats.forEach { moodCount ->
                        Text(
                            text = moodCount.mood.capitalize(),
                            fontSize = 10.sp,
                            fontFamily = sonoFamily,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No mood data yet",
                        fontSize = 14.sp,
                        fontFamily = sonoFamily,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

private fun getMoodValue(mood: String): Float {
    return when (mood.lowercase()) {
        "happy" -> 0.9f
        "excited" -> 1.0f
        "sad" -> 0.2f
        "angry" -> 0.1f
        "anxious" -> 0.3f
        "calm" -> 0.7f
        "neutral" -> 0.5f
        else -> 0.5f
    }
}

private fun getMoodFromValue(value: Float): String {
    return when {
        value >= 0.8f -> "happy"
        value >= 0.6f -> "calm"
        value >= 0.4f -> "neutral"
        value >= 0.2f -> "sad"
        else -> "angry"
    }
}
