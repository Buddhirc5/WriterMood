package com.grayseal.notesapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.grayseal.notesapp.data.MoodCount
import com.grayseal.notesapp.model.Note
import com.grayseal.notesapp.ui.theme.*
import com.grayseal.notesapp.ui.components.MoodTrendChart
import com.grayseal.notesapp.ui.components.MoodPieChart
import com.grayseal.notesapp.ui.components.MoodBarChart
import com.grayseal.notesapp.util.MoodUtils.getMoodColor
import com.grayseal.notesapp.util.MoodUtils.capitalize
import com.grayseal.notesapp.ui.theme.ThemeManager
import com.grayseal.notesapp.util.PerformanceOptimizer
import kotlinx.coroutines.delay

@Composable
fun MoodDashboardScreen(
    viewModel: NoteViewModel,
    onNavigateToHome: () -> Unit
) {
    val context = LocalContext.current
    val notesList by viewModel.noteList.collectAsState()
    val moodStats = remember { mutableStateListOf<MoodCount>() }
    val motivationalQuote = remember { mutableStateOf("") }
    
    // Performance optimization for low-end devices
    LaunchedEffect(Unit) {
        if (PerformanceOptimizer.isLowMemoryDevice(context)) {
            PerformanceOptimizer.optimizeForLowEndDevice()
        }
    }
    
    // Generate real mood stats from actual notes
    LaunchedEffect(notesList) {
        val moodCounts = mutableMapOf<String, Int>()
        notesList.forEach { note ->
            val mood = note.mood
            moodCounts[mood] = (moodCounts[mood] ?: 0) + 1
        }
        
        moodStats.clear()
        moodCounts.forEach { (mood, count) ->
            moodStats.add(MoodCount(mood, count))
        }
    }
    
    // Rotating motivational quotes
    LaunchedEffect(Unit) {
        val quotes = listOf(
            "Every word you write is a step toward your masterpiece.",
            "Your emotions fuel your creativity - embrace them.",
            "Writing is the art of turning thoughts into magic.",
            "Your unique voice deserves to be heard.",
            "Every mood has its own story to tell.",
            "Writing heals the soul and enlightens the mind.",
            "Your words have the power to change the world.",
            "Embrace your writing journey, one mood at a time."
        )
        
        while (true) {
            motivationalQuote.value = quotes.random()
            delay(8000) // Change quote every 8 seconds
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Mood Dashboard",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontFamily = sonoFamily,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateToHome) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                backgroundColor = ThemeManager.getPrimaryColor()
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
                        item {
                                    Text(
                        text = "Your Writing Mood Journey",
                        style = TextStyle(
                            color = ThemeManager.getPrimaryColor(),
                            fontSize = 30.sp,
                            fontFamily = sonoFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
            }
            

            
            item {
                MoodSummaryCard(moodStats)
            }
            
            item {
                MoodTrendChart(notes = notesList)
            }
            
            item {
                MoodPieChart(moodStats = moodStats)
            }
            
            item {
                MoodBarChart(moodStats = moodStats)
            }
            
            item {
                                    Text(
                        text = "Mood Timeline",
                        style = TextStyle(
                            color = ThemeManager.getPrimaryColor(),
                            fontSize = 20.sp,
                            fontFamily = sonoFamily,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
            }
            
            item {
                MoodTimelineCard(notesList)
            }
            
            item {
                                    Text(
                        text = "Mood Breakdown",
                        style = TextStyle(
                            color = ThemeManager.getPrimaryColor(),
                            fontSize = 20.sp,
                            fontFamily = sonoFamily,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
            }
            
            items(moodStats) { moodCount ->
                MoodItemCard(moodCount)
            }
            
            if (moodStats.isEmpty()) {
                item {
                    EmptyMoodCard()
                }
            }
        }
    }
}

@Composable
fun MoodSummaryCard(moodStats: List<MoodCount>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Total Notes: ${moodStats.sumOf { it.count }}",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = sonoFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            val dominantMood = moodStats.maxByOrNull { it.count }
            if (dominantMood != null) {
                Text(
                    text = "Most Common Mood: ${dominantMood.mood.capitalize()}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = sonoFamily,
                        fontWeight = FontWeight.Medium,
                        color = getMoodColor(dominantMood.mood)
                    )
                )
            }
        }
    }
}

@Composable
fun MoodItemCard(moodCount: MoodCount) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 2.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = getMoodColor(moodCount.mood),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getMoodIcon(moodCount.mood),
                    contentDescription = moodCount.mood,
                    tint = Color.White
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                            Text(
                text = moodCount.mood.capitalize(),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = sonoFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
            )
            Text(
                text = "${moodCount.count} notes",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = sonoFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF92a4a2)
                )
            )
            }
            
            Text(
                text = "${moodCount.count}",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = sonoFamily,
                    fontWeight = FontWeight.Bold,
                    color = getMoodColor(moodCount.mood)
                )
            )
        }
    }
}

@Composable
fun EmptyMoodCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 2.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.SentimentSatisfied,
                contentDescription = "No moods yet",
                modifier = Modifier.size(64.dp),
                tint = Color.Gray
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "No mood data yet",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = sonoFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF92a4a2)
                )
            )
            
            Text(
                text = "Start writing notes to see your mood patterns",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = sonoFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF92a4a2)
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
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

@Composable
fun getMoodIcon(mood: String) = when (mood.lowercase()) {
    "happy" -> Icons.Default.SentimentSatisfied
    "sad" -> Icons.Default.SentimentDissatisfied
    "angry" -> Icons.Default.SentimentVeryDissatisfied
    "love" -> Icons.Default.Favorite
    "fear" -> Icons.Default.Warning
    "surprise" -> Icons.Default.SentimentSatisfiedAlt
    else -> Icons.Default.SentimentNeutral
}

fun String.capitalize(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}

@Composable
fun MotivationalQuoteCard(quote: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = ThemeManager.getBackgroundColor()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = "Inspiration",
                    tint = ThemeManager.getPrimaryColor(),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Writer's Inspiration",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = sonoFamily,
                        fontWeight = FontWeight.Bold,
                        color = ThemeManager.getPrimaryColor()
                    )
                )
            }
            Text(
                text = quote,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = sonoFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF666666)
                )
            )
        }
    }
}

@Composable
fun MoodTimelineCard(notes: List<Note>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Recent Mood Changes",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = sonoFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            if (notes.isNotEmpty()) {
                // Show recent notes with their moods
                val recentNotes = notes.take(5)
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    recentNotes.forEach { note ->
                        MoodTimelineItem(
                            note.mood,
                            note.entry_date,
                            getMoodColor(note.mood)
                        )
                    }
                }
            } else {
                Text(
                    text = "No notes yet. Start writing to see your mood timeline!",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = sonoFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF92a4a2)
                    )
                )
            }
        }
    }
}

@Composable
fun MoodTimelineItem(mood: String, timeAgo: String, moodColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(
                    color = moodColor,
                    shape = androidx.compose.foundation.shape.CircleShape
                )
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = mood.capitalize(),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = sonoFamily,
                    fontWeight = FontWeight.Medium,
                    color = moodColor
                )
            )
            Text(
                text = timeAgo,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = sonoFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF92a4a2)
                )
            )
        }
        
        Icon(
            imageVector = getMoodIcon(mood),
            contentDescription = mood,
            tint = moodColor,
            modifier = Modifier.size(20.dp)
        )
    }
}
