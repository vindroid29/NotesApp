package com.globant.notesapp.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = Primary,
    surface = Surface,
    onSurface = OnSurface,
    background = Color.White,
    onSurfaceVariant = OnSurfaceVariant,
    surfaceContainerLowest = SurfaceLowest,
    onPrimary = Color.White,
)
val DarkColorScheme = darkColorScheme(
)


@Composable
fun NoteAppTheme(content: @Composable () -> Unit) {
    val themeColors = if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = themeColors,
        typography = Typography,
        content = content
    )

}


