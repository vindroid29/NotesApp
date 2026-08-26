package com.globant.notesapp.presentation.ui.notedetails

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun NoteDetailsScreen() {

    val noteDetailsViewModel = koinViewModel<NoteDetailsViewModel>()

}