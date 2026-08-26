package com.globant.notesapp.di

import com.globant.notesapp.database.NotesDatabase
import com.globant.notesapp.database.getNotesDatabaseBuilder
import org.koin.dsl.module

actual fun platformModule() = module {
    single<NotesDatabase> { getNotesDatabaseBuilder(get())}
}