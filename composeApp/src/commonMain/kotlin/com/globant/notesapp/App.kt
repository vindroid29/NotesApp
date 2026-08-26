package com.globant.notesapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.globant.notesapp.presentation.ui.notes.NotesListScreen
import com.globant.notesapp.theme.NoteAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    NoteAppTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Notes", style = MaterialTheme.typography.titleLarge) })
            },
            content = {
                Surface(modifier = Modifier.padding(it).fillMaxSize()) {
                    NotesListScreen()
                }
            }
        )
    }
}

val LocalShape = compositionLocalOf {
    RectangleShape
}

data object TriangleShape : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = Path().apply {
                moveTo(
                    x = size.width / 2f,
                    y = 0f
                )
                lineTo(
                    x = 0f,
                    y = size.height
                )
                lineTo(
                    x = size.width,
                    y = size.height
                )
                close()
            }
        )
    }
}

