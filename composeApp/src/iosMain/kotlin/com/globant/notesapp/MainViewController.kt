package com.globant.notesapp

import androidx.compose.ui.window.ComposeUIViewController
import com.globant.notesapp.di.initKoin

fun MainViewController() = ComposeUIViewController(configure = { initKoin() })
{
    App()
}