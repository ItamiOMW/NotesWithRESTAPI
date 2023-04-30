package com.example.noteswithrestapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.noteswithrestapi.core.ui.theme.NotesWithRESTAPITheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesWithRESTAPITheme() {
                val systemUiController = rememberSystemUiController().apply {
                    this.setSystemBarsColor(MaterialTheme.colorScheme.surface)
                }
            }
        }
    }
}
