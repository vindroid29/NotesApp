package com.globant.notesapp.presentation.ui.notes

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class InsertNoteUseCase : KoinComponent {
    val notesRepository: NotesRepository by inject()

    suspend fun upsertNote(note: Note) = notesRepository.upsertNote(note)

    suspend fun deleteNote(note: Note) = notesRepository.deleteNote(note)

}