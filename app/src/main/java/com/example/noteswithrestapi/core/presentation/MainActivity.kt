package com.example.noteswithrestapi.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import com.example.noteswithrestapi.authentication_feature.data.repository.AuthenticationRepositoryImpl
import com.example.noteswithrestapi.core.presentation.navigation.RootNavGraph
import com.example.noteswithrestapi.core.presentation.theme.ui.theme.NotesWithRESTAPITheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authenticationRepositoryImpl: AuthenticationRepositoryImpl

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesWithRESTAPITheme {
                val systemUiController = rememberSystemUiController().apply {
                    this.setSystemBarsColor(MaterialTheme.colorScheme.surface)
                }
                val navController = rememberAnimatedNavController()
                RootNavGraph(navController = navController)
            }
        }
    }
}
