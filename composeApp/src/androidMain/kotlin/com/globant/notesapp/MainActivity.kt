package com.globant.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(/*statusBarStyle = SystemBarStyle.dark(0)*/)
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}