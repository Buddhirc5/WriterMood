package com.grayseal.notesapp.screens

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grayseal.notesapp.data.MoodCount
import com.grayseal.notesapp.model.Note
import com.grayseal.notesapp.repository.NoteRepository
import com.grayseal.notesapp.util.PerformanceUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository): ViewModel() {
    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()

    private val _detectedMood = MutableStateFlow("neutral")
    val detectedMood = _detectedMood.asStateFlow()

    // private val _moodStats = MutableStateFlow<List<MoodCount>>(emptyList())
    // val moodStats = _moodStats.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().distinctUntilChanged().collect { listOfNotes ->
                if (listOfNotes.isEmpty()) {
                    _noteList.value = emptyList()
                    Log.d("Empty", ": Empty list")
                } else {
                    _noteList.value = listOfNotes
                }
            }
        }
        // loadMoodStats()
    }

    fun addNote(note: Note) = viewModelScope.launch { 
        PerformanceUtils.executeWithLogging("Add Note") {
            repository.addNoteWithMoodDetection(note)
        }
        // loadMoodStats()
    }
    fun updateNote(note: Note) = viewModelScope.launch { 
        PerformanceUtils.executeWithLogging("Update Note") {
            repository.updateNote(note)
        }
    }
    fun deleteNote(note: Note) = viewModelScope.launch { 
        PerformanceUtils.executeWithLogging("Delete Note") {
            repository.deleteNote(note)
        }
        // loadMoodStats()
    }
    fun deleteAllNotes() = viewModelScope.launch { repository.deleteAllNotes() }
    fun getAllNotes() = viewModelScope.launch { repository.getAllNotes() }

    fun detectMood(text: String) = viewModelScope.launch {
        _detectedMood.value = repository.detectMood(text)
    }
    
    // private fun loadMoodStats() = viewModelScope.launch(Dispatchers.IO) {
    //     _moodStats.value = repository.getNotesByMood()
    // }
}