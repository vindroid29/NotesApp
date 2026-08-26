package com.globant.notesapp.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.globant.notesapp.presentation.ui.notes.Note


@Database(
    entities = [Note::class], version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class NotesDatabase() : RoomDatabase(), DB {

    abstract fun notesDao(): NoteDao

    override fun clearAllTables(): Unit {}

}

interface DB {
    fun clearAllTables(): Unit
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<NotesDatabase> {
    override fun initialize(): NotesDatabase
}
