package com.globant.notesapp.presentation.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.notesapp.core.common.UiState
import com.globant.notesapp.core.common.asResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NoteListViewModel(getAllNotesUseCase: GetAllNotesUseCase) : ViewModel(), KoinComponent {
    private val insertNoteUseCase: InsertNoteUseCase by inject()

    private val _addNoteDialogState = MutableStateFlow(false)
    var addNoteDialogState: StateFlow<Boolean> = _addNoteDialogState

    init {
        println("NoteListViewModel initiated...")
        /*viewModelScope.launch(Dispatchers.IO) {
            delay(3000)
            insertDummyData()
        }*/
    }

    var notesListState: StateFlow<UiState<List<Note>>> = getAllNotesUseCase()
        .asResult()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading,
        )

    fun upsertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        insertNoteUseCase.upsertNote(note)
        _addNoteDialogState.update { false }
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        insertNoteUseCase.deleteNote(note)
    }

    fun showAddNoteBottomsheet() {
        _addNoteDialogState.value = true
    }

    fun dismissAddNoteBottomsheet() {
        _addNoteDialogState.value = false
    }

    private fun insertDummyData() {
        sampleRecords.forEach {
            upsertNote(it)
        }
    }

    val sampleRecords = arrayListOf(
        Note(title = "First Entry", content = "This is the content for the first item."),
        Note(title = "Meeting Notes", content = "Discuss project timelines and budget allocation."),
        Note(title = "Shopping List", content = "Milk, eggs, bread, and coffee beans."),
        Note(title = "Reminder", content = "Don't forget to call the dentist at 2 PM."),
        Note(title = "Idea", content = "Build a mobile app using Kotlin and Jetpack Compose."),
        Note(title = "Book Recommendation", content = "Clean Code by Robert C. Martin."),
        Note(title = "Workout Plan", content = "30 minutes of cardio and 15 minutes of core."),
        Note(title = "Recipe", content = "Spaghetti Carbonara: eggs, guanciale, and pecorino."),
        Note(
            title = "Travel Goal", content = "Plan a trip to Japan for the cherry blossom season."
        ),
        Note(title = "Learning Tip", content = "Practice coding every day to build muscle memory."),
        Note(title = "Project Milestone", content = "Finalize the UI mockups for the dashboard."),
        Note(title = "Language Learning", content = "Review 20 new kanji characters in Anki."),
        Note(title = "Cooking Goal", content = "Master the technique for a perfect French omelet."),
        Note(title = "Tech News", content = "Read about the latest updates in Kotlin 2.0."),
        Note(title = "Pet Care", content = "Schedule a grooming appointment for the cat."),
        Note(title = "Home Improvement", content = "Replace the air filters in the HVAC system."),
        Note(
            title = "Quick Tip",
            content = "Use 'Ctrl + Alt + L' to reformat code in Android Studio."
        ),
        Note(
            title = "Movie List", content = "Watch 'Interstellar' for the third time this weekend."
        ),
        Note(title = "Garden Diary", content = "The tomato plants are finally starting to sprout."),
        Note(title = "Financial Note", content = "Move $200 to the emergency savings account.")

    )

}