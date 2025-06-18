package com.example.calendaremainder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

// PUBLIC_INTERFACE
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContainerScaffold()
        }
    }
}
