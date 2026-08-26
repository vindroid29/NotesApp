package com.globant.notesapp.presentation.ui.notes

import com.globant.notesapp.database.NotesDatabase
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun upsertNote(note: Note): Long

    suspend fun deleteNote(note: Note)
}

class NotesRepositoryImpl(val database: NotesDatabase) : NotesRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return database.notesDao().getAllNotes()
    }

    override suspend fun upsertNote(note: Note): Long {
        return database.notesDao().upsertNote(note = note)
    }

    override suspend fun deleteNote(note: Note) {
        database.notesDao().deleteNote(note)
    }

}