package com.globant.notesapp.presentation.ui.notes

import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

class GetAllNotesUseCase(val notesRepository: NotesRepository) : KoinComponent {

    operator fun invoke(): Flow<List<Note>> = notesRepository.getAllNotes()

}