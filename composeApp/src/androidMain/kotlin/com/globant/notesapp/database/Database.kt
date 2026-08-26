package com.globant.notesapp.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver


fun getNotesDatabaseBuilder(context: Context): NotesDatabase {
    val dbNotes = context.getDatabasePath("notes.db")
    return Room.databaseBuilder<NotesDatabase>(
        context = context.applicationContext, name = dbNotes.absolutePath
    ).setDriver(BundledSQLiteDriver()).build()
}