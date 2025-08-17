package com.grayseal.notesapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.filled.SentimentSatisfied
import com.grayseal.notesapp.util.MoodUtils.getMoodColor
import com.grayseal.notesapp.util.MoodUtils.capitalize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.grayseal.notesapp.model.Note
import com.grayseal.notesapp.navigation.NoteScreens
import com.grayseal.notesapp.ui.theme.sonoFamily
import com.grayseal.notesapp.util.getCurrentDate
import com.grayseal.notesapp.ui.theme.ThemeManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun NoteScreen(
    navController: NavController, 
    noteViewModel: NoteViewModel,
    editMode: Boolean = false,
    noteToEdit: Note? = null
) {
    NoteContent(
        navController = navController, 
        noteViewModel = noteViewModel,
        editMode = editMode,
        noteToEdit = noteToEdit
    )
}

@Composable
fun NoteContent(
    navController: NavController, 
    noteViewModel: NoteViewModel,
    editMode: Boolean = false,
    noteToEdit: Note? = null
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp), horizontalArrangement = Arrangement.Center
            ) {
                LeadingIconTab(
                    selected = false,
                    enabled = false,
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            modifier = Modifier.size(60.dp),
                            imageVector = Icons.Outlined.EmojiNature,
                            contentDescription = "Notes",
                            tint = ThemeManager.getPrimaryColor()
                        )
                    },
                    text = {
                        Text(
                            if (editMode) "Edit Note" else "Note",
                            color = ThemeManager.getPrimaryColor(),
                            style = (TextStyle(
                                fontSize = 30.sp,
                                fontFamily = sonoFamily,
                                fontWeight = FontWeight.Bold
                            ))
                        )
                    })
            }
            NoteArea(
                navController = navController, 
                noteViewModel = noteViewModel,
                editMode = editMode,
                noteToEdit = noteToEdit
            )
        }
    }
}

@Composable
fun NoteArea(
    navController: NavController, 
    noteViewModel: NoteViewModel,
    editMode: Boolean = false,
    noteToEdit: Note? = null
) {
    var note by remember {
        mutableStateOf(noteToEdit?.note ?: "")
    }
    var title by remember {
        mutableStateOf(noteToEdit?.title ?: "")
    }
    var detectedMood by remember {
        mutableStateOf(noteToEdit?.mood ?: "neutral")
    }
    
    // Update detected mood when note changes
    LaunchedEffect(note) {
        if (note.isNotEmpty()) {
            detectedMood = com.grayseal.notesapp.data.MoodDetectionService.detectMood(note)
        }
    }
    
    if (editMode) {
        SaveButton(
            navController = navController, 
            title, 
            note, 
            onSaveNote = { 
                val updatedNote = noteToEdit?.copy(
                    title = title,
                    note = note,
                    mood = detectedMood
                )
                if (updatedNote != null) {
                    noteViewModel.updateNote(updatedNote)
                }
            },
            buttonText = "Update"
        )
    } else {
        SaveButton(
            navController = navController, 
            title, 
            note, 
            onSaveNote = { noteViewModel.addNote(it) }
        )
    }
    
    Note(title = title, note = note, onTitleChange = { title = it }, onNoteChange = { note = it })
    MoodIndicator(detectedMood = detectedMood)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Note(
    title: String,
    note: String,
    onTitleChange: (String) -> Unit,
    onNoteChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    /*Date Row*/
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 10.dp, start = 15.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = getCurrentDate(),
            style = (TextStyle(fontSize = 18.sp, color = ThemeManager.getPrimaryColor())),
            fontFamily = sonoFamily,
            fontWeight = FontWeight.Normal
        )
    }

    /*Note Title Row*/
    Row(horizontalArrangement = Arrangement.Start) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = title,
            onValueChange = onTitleChange,
            placeholder = {
                Text(
                    text = "Note Title",
                    color = ThemeManager.getTextColor().copy(alpha = 0.6f),
                    fontSize = 18.sp,
                    fontFamily = sonoFamily,
                    fontWeight = FontWeight.Normal
                )
            },
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = ThemeManager.getTextColor(),
                fontFamily = sonoFamily,
                fontWeight = FontWeight.Normal
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Ascii,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions {
                keyboardController?.hide()
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF4c6569),
                backgroundColor = MaterialTheme.colors.background,
            )
        )
    }

    /*Note Description Row*/
    Row(horizontalArrangement = Arrangement.Start) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = note,
            onValueChange = onNoteChange,
            placeholder = {
                Text(
                    text = "Write down something...",
                    color = ThemeManager.getTextColor().copy(alpha = 0.6f),
                    fontSize = 18.sp,
                    fontFamily = sonoFamily,
                    fontWeight = FontWeight.Light
                )
            },
            singleLine = false,
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = ThemeManager.getTextColor(),
                fontFamily = sonoFamily,
                fontWeight = FontWeight.Normal
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Ascii,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions {
                keyboardController?.hide()
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF4c6569),
                backgroundColor = MaterialTheme.colors.background,
            )
        )
    }
}

@Composable
fun SaveButton(
    navController: NavController,
    title: String,
    note: String,
    onSaveNote: (Note) -> Unit,
    buttonText: String = "Save"
) {
    var openDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.End
    ) {
        AlertDialog(openDialog = openDialog, onDismiss = {
            openDialog = false
        })
        TextButton(
            onClick = {
                if (title.isNotEmpty() && note.isNotEmpty()) {
                    onSaveNote(Note(title = title, note = note))
                    Toast.makeText(context, if (buttonText == "Update") "Note Updated" else "Note Saved", Toast.LENGTH_SHORT).show()
                    /*Save data
                    title = ""
                    note = ""
                    */
                    navController.navigate(route = NoteScreens.HomeScreen.name, )
                } else {
                    openDialog = true
                }
            },
            enabled = true,
            contentPadding = PaddingValues(5.dp),
            colors = ButtonDefaults.textButtonColors(contentColor = ThemeManager.getPrimaryColor())
        ) {
            Text(
                buttonText,
                style = (TextStyle(color = ThemeManager.getPrimaryColor(), fontSize = 30.sp)),
                fontFamily = sonoFamily,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AlertDialog(openDialog: Boolean, onDismiss: () -> Unit) {
    if (openDialog) {
        AlertDialog(
            /* Dismiss the dialog when the user clicks outside the dialog or on the back
                   button. If you want to disable that functionality, simply use an empty
                   onDismissRequest. */
            onDismissRequest = onDismiss,
            title = {
                Row {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Alert",
                        tint = ThemeManager.getPrimaryColor(),
                    )

                    Text(
                        "Alert",
                        color = ThemeManager.getPrimaryColor(),
                        fontSize = 20.sp,
                        fontFamily = sonoFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            text = {
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = "Don't forget to enter your Note Title and Note",
                    color = colors.onBackground,
                    fontSize = 16.sp,
                    fontFamily = sonoFamily,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                                    Text(
                    "OK",
                    style = (TextStyle(color = ThemeManager.getPrimaryColor(), fontSize = 20.sp)),
                    fontFamily = sonoFamily,
                    fontWeight = FontWeight.Bold
                )
                }
            }
        )
    }
}

@Composable
fun MoodIndicator(detectedMood: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.SentimentSatisfied,
            contentDescription = "Mood",
            tint = getMoodColor(detectedMood),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Detected Mood: ${detectedMood.capitalize()}",
            style = TextStyle(
                fontSize = 14.sp,
                color = getMoodColor(detectedMood),
                fontFamily = sonoFamily,
                fontWeight = FontWeight.Medium
            )
        )
    }
}



