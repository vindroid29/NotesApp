package com.globant.notesapp

import android.app.Application
import com.globant.notesapp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent

class NoteApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@NoteApplication)
        }
    }
}