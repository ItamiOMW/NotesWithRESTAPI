package com.example.noteswithrestapi.note_feature.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.noteswithrestapi.core.presentation.navigation.Graph
import com.example.noteswithrestapi.core.presentation.navigation.Screen
import com.example.noteswithrestapi.note_feature.presentation.notes.NotesScreen
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.noteGraph(
    navController: NavController,
) {
    navigation(
        route = Graph.NOTE_GRAPH,
        startDestination = Screen.Notes.fullRoute
    ) {
        composable(Screen.Notes.fullRoute) {
            NotesScreen()
        }
    }
}