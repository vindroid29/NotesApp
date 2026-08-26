package com.globant.notesapp.di

import com.globant.notesapp.presentation.ui.notedetails.NoteDetailsViewModel
import com.globant.notesapp.presentation.ui.notes.GetAllNotesUseCase
import com.globant.notesapp.presentation.ui.notes.InsertNoteUseCase
import com.globant.notesapp.presentation.ui.notes.NoteListViewModel
import com.globant.notesapp.presentation.ui.notes.NotesRepository
import com.globant.notesapp.presentation.ui.notes.NotesRepositoryImpl
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module


expect fun platformModule(): Module

val networkModule = module {

}

val viewModelModule = module {
    factoryOf(::NoteListViewModel)
    factoryOf(::NoteDetailsViewModel)
}

val provideRepositoryModule = module {
    singleOf(::NotesRepositoryImpl).bind(NotesRepository::class)
}

val provideUseCaseModule = module {
    singleOf(::GetAllNotesUseCase)
    singleOf(::InsertNoteUseCase)
}

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(viewModelModule, provideRepositoryModule, provideUseCaseModule, platformModule())
    }
}

