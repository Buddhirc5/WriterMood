package com.grayseal.notesapp.repository

import com.grayseal.notesapp.data.MoodDetectionService
import com.grayseal.notesapp.data.NoteDatabaseDao
import com.grayseal.notesapp.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDatabaseDao: NoteDatabaseDao,
    private val moodDetectionService: MoodDetectionService
){
    private val moodCache = mutableMapOf<String, String>()
    suspend fun addNote(note: Note) = noteDatabaseDao.insert(note = note)
    suspend fun updateNote(note: Note) = noteDatabaseDao.update(note)
    suspend fun deleteNote(note: Note) = noteDatabaseDao.deleteNote(note)
    suspend fun deleteAllNotes() = noteDatabaseDao.deleteAll()
    suspend fun getAllNotes(): Flow<List<Note>> = noteDatabaseDao.getNotes().flowOn(Dispatchers.IO).conflate()
    
    suspend fun addNoteWithMoodDetection(note: Note): Note {
        val detectedMood = detectMood(note.note)
        val noteWithMood = note.copy(mood = detectedMood)
        noteDatabaseDao.insert(noteWithMood)
        return noteWithMood
    }

    suspend fun detectMood(text: String): String {
        moodCache[text]?.let { return it }

        val networkMood = moodDetectionService.detectMood(text)
        return if (networkMood != null) {
            moodCache[text] = networkMood
            networkMood
        } else {
            moodCache[text] ?: MoodDetectionService.fallbackMood(text)
        }
    }
    
    // suspend fun getNotesByMood(): List<MoodCount> = noteDatabaseDao.getNotesByMood()
    // suspend fun getNotesByMood(mood: String): List<Note> = noteDatabaseDao.getNotesByMood(mood)
}