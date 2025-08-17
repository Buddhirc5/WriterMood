package com.grayseal.notesapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.core.tween
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material.icons.sharp.Eco
import androidx.compose.material.icons.sharp.Edit
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Edit
import com.grayseal.notesapp.util.MoodUtils.getMoodColor
import com.grayseal.notesapp.util.MoodUtils.capitalize
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.grayseal.notesapp.model.Note
import com.grayseal.notesapp.navigation.NoteScreens
import com.grayseal.notesapp.ui.theme.sonoFamily
import com.grayseal.notesapp.ui.theme.ThemeManager
import kotlinx.coroutines.delay
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.Alignment

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(navController: NavController, noteViewModel: NoteViewModel) {
    val currentTheme by remember { mutableStateOf(ThemeManager.currentTheme) }
    
    Scaffold(
        floatingActionButton = { FloatingAddNoteButton(navController = navController) },
        topBar = {
            TopAppBar(
                title = { 
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(
                            initialOffsetY = { -it },
                            animationSpec = tween(1000)
                        )
                    ) {
                        Text(
                            "WriterMood",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 20.sp,
                                fontFamily = sonoFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                },
                actions = {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(1000, delayMillis = 300)) + scaleIn(
                            animationSpec = tween(1000, delayMillis = 300)
                        )
                    ) {
                        IconButton(
                            onClick = { navController.navigate(NoteScreens.ThemeChangerScreen.name) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Palette,
                                contentDescription = "Theme Changer",
                                tint = Color.White
                            )
                        }
                    }
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(1000, delayMillis = 500)) + scaleIn(
                            animationSpec = tween(1000, delayMillis = 500)
                        )
                    ) {
                        IconButton(
                            onClick = { navController.navigate(NoteScreens.MoodDashboardScreen.name) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Psychology,
                                contentDescription = "Mood Dashboard",
                                tint = Color.White
                            )
                        }
                    }
                },
                backgroundColor = ThemeManager.getPrimaryColor()
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
            ) {
                Avatar()
                
                // Writer's Inspiration Section
                WritersInspirationSection()
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, start = 20.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Saved Notes",
                        style = (TextStyle(color = ThemeManager.getPrimaryColor(), fontSize = 30.sp)),
                        fontFamily = sonoFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
                Home(noteViewModel = noteViewModel, navController = navController)
            }
        },
    )
}
@Composable
fun Home(noteViewModel: NoteViewModel, navController: NavController){
    /*get all Notes*/
    val notesList = noteViewModel.noteList.collectAsState().value
    HomeContent(
        notes = notesList, 
        onRemoveNote = {noteViewModel.deleteNote(it)},
        onEditNote = {noteViewModel.updateNote(it)},
        navController = navController
    )
}

@Composable
fun HomeContent(
    notes: List<Note>,
    onRemoveNote: (Note) -> Unit,
    onEditNote: (Note) -> Unit,
    navController: NavController
) {

    Column(modifier = Modifier.padding(20.dp)) {
        LazyColumn {
            items(notes) {
                NoteCard(
                    note = it, 
                    onDeleteClick = { onRemoveNote(it) },
                    onEditClick = { onEditNote(it) },
                    navController = navController
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCard(
    note: Note, 
    onDeleteClick: (Note) -> Unit, 
    onEditClick: (Note) -> Unit,
    navController: NavController
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    
    // Delete confirmation dialog
    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                onDeleteClick(note)
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }
    
    // Edit functionality - navigate to edit screen
    if (showEditDialog) {
        // Navigate to edit screen with note data
        val encodedTitle = java.net.URLEncoder.encode(note.title, "UTF-8")
        val encodedContent = java.net.URLEncoder.encode(note.note, "UTF-8")
        val encodedMood = java.net.URLEncoder.encode(note.mood, "UTF-8")
        
        navController.navigate(
            "edit_note/${note.id}/$encodedTitle/$encodedContent/$encodedMood"
        )
        showEditDialog = false
    }
    
    OutlinedCard(
        onClick = {
            showEditDialog = true
        },
        enabled = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 20.dp)
            .clip(RoundedCornerShape(10.dp)),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(0.2.dp, color = ThemeManager.getBorderColor()),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                note.title,
                fontSize = 18.sp,
                color = Color.DarkGray,
                fontFamily = sonoFamily,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(15.dp))
            Text(
                note.note,
                fontSize = 14.sp,
                color = Color(0xFF92a4a2),
                fontFamily = sonoFamily,
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.height(15.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.SentimentSatisfied,
                    contentDescription = "Mood",
                    tint = getMoodColor(note.mood),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = note.mood.capitalize(),
                    fontSize = 12.sp,
                    color = getMoodColor(note.mood),
                    fontFamily = sonoFamily
                )
            }
            Text(note.entry_date, fontSize = 12.sp, color = Color.LightGray, fontFamily = sonoFamily)
            Spacer(Modifier.weight(1f))
            // Edit button
            Icon(
                modifier = Modifier
                    .clickable { showEditDialog = true }
                    .padding(end = 8.dp),
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit",
                tint = ThemeManager.getPrimaryColor()
            )
            
            // Delete button
            Icon(
                modifier = Modifier.clickable {
                    showDeleteDialog = true
                },
                imageVector = Icons.Sharp.Delete,
                contentDescription = "Delete",
                tint = Color.Red.copy(alpha = .5f)
            )
        }
    }
}

@Composable
fun FloatingAddNoteButton(navController: NavController) {
    FloatingActionButton(
        modifier = Modifier.padding(bottom = 30.dp),
        onClick = { navController.navigate(route = NoteScreens.NoteScreen.name) },
        containerColor = ThemeManager.getPrimaryColor()
    )

    {
        Icon(
            imageVector = Icons.Sharp.Edit,
            contentDescription = "Add Note",
            tint = Color.White
        )
    }
}

@Composable
fun Avatar() {
    // Removed the leaf icon as requested
    Spacer(modifier = Modifier.height(20.dp))
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WritersInspirationSection() {
    val motivationalQuote = remember { mutableStateOf("") }
    
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
    
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(1000, delayMillis = 300)) + slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(1000, delayMillis = 300)
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(10.dp),
            backgroundColor = ThemeManager.getBackgroundColor()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    AnimatedVisibility(
                        visible = true,
                        enter = scaleIn(animationSpec = tween(1000, delayMillis = 600))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Lightbulb,
                            contentDescription = "Inspiration",
                            tint = ThemeManager.getPrimaryColor(),
                            modifier = Modifier.size(20.dp)
                        )
                    }
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
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(1000, delayMillis = 900))
                ) {
                    Text(
                        text = motivationalQuote.value,
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
    }
}

@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Warning",
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Delete Note",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = sonoFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                )
            }
        },
        text = {
            Text(
                text = "Are you sure you want to delete this note? This action cannot be undone.",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = sonoFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray
                )
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
            ) {
                Text(
                    "Delete",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = sonoFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    "Cancel",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = sonoFamily,
                        fontWeight = FontWeight.Medium,
                        color = ThemeManager.getPrimaryColor()
                    )
                )
            }
        }
    )
}


