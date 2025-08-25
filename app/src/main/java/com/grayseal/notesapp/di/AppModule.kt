package com.grayseal.notesapp.di

import android.content.Context
import androidx.room.Room
import com.grayseal.notesapp.BuildConfig
import com.grayseal.notesapp.data.MoodDetectionService
import com.grayseal.notesapp.data.NoteDatabase
import com.grayseal.notesapp.data.NoteDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class) //Installs AppModule in the generate SingletonComponent.
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideNotesDao(noteDatabase: NoteDatabase): NoteDatabaseDao = noteDatabase.noteDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(
            context, NoteDatabase::class.java, "notes_db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    @Named("huggingFaceApiKey")
    fun provideHuggingFaceApiKey(): String = BuildConfig.HUGGING_FACE_API_KEY

    @Provides
    @Singleton
    fun provideMoodDetectionService(@Named("huggingFaceApiKey") apiKey: String): MoodDetectionService =
        MoodDetectionService(apiKey)
}