package com.example.noteswithrestapi.note_feature.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.noteswithrestapi.core.presentation.navigation.Graph
import com.example.noteswithrestapi.core.presentation.navigation.Screen
import com.example.noteswithrestapi.note_feature.presentation.add_note.AddNoteScreen
import com.example.noteswithrestapi.note_feature.presentation.add_note.AddNoteViewModel
import com.example.noteswithrestapi.note_feature.presentation.edit_note.EditNoteScreen
import com.example.noteswithrestapi.note_feature.presentation.edit_note.EditNoteViewModel
import com.example.noteswithrestapi.note_feature.presentation.notes.NotesScreen
import com.example.noteswithrestapi.note_feature.presentation.notes.NotesViewModel
import com.example.noteswithrestapi.note_feature.presentation.search_note.SearchNoteScreen
import com.example.noteswithrestapi.note_feature.presentation.search_note.SearchNoteViewModel
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.noteGraph(
    navController: NavController,
    onShowNavigationDrawer: () -> Unit,
) {
    navigation(
        route = Graph.NOTE_GRAPH,
        startDestination = Screen.Notes.fullRoute
    ) {
        composable(Screen.Notes.fullRoute) {
            val viewModel: NotesViewModel = hiltViewModel()
            NotesScreen(
                onNavigateToAddNote = {
                    navController.navigate(Screen.AddNote.fullRoute) {
                        navController.currentDestination?.id?.let { id ->
                            popUpTo(id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                },
                onNavigateToEditNote = { noteId ->
                    navController.navigate(Screen.EditNote.getRouteWithArgs(noteId)) {
                        navController.currentDestination?.id?.let { id ->
                            popUpTo(id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                },
                onNavigateToSearchNote = {
                    navController.navigate(Screen.SearchNote.fullRoute) {
                        navController.currentDestination?.id?.let { id ->
                            popUpTo(id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                },
                onShowNavigationDrawer = onShowNavigationDrawer,
                state = viewModel.state,
                onEvent = viewModel::onEvent
            )
        }
        composable(
            Screen.AddNote.fullRoute,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it }, animationSpec = tween(300, easing = FastOutSlowInEasing)
                ).plus(fadeIn(tween(300)))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it }, animationSpec = tween(300, easing = LinearOutSlowInEasing)
                ).plus(fadeOut(tween(300)))
            }
        ) {
            val viewModel: AddNoteViewModel = hiltViewModel()
            AddNoteScreen(
                onSavedSuccessfully = {
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                },
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(
            Screen.EditNote.fullRoute,
            arguments = listOf(
                navArgument(Screen.NOTE_ID_ARG) {
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it }, animationSpec = tween(300, easing = FastOutSlowInEasing)
                ).plus(fadeIn(tween(300)))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it }, animationSpec = tween(300, easing = LinearOutSlowInEasing)
                ).plus(fadeOut(tween(300)))
            }
        ) {
            val viewModel: EditNoteViewModel = hiltViewModel()
            EditNoteScreen(
                onSavedSuccessfully = { navController.popBackStack() },
                onDeletedSuccessfully = { navController.popBackStack() },
                onCancel = { navController.popBackStack() },
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(
            Screen.SearchNote.fullRoute,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it }, animationSpec = tween(300, easing = FastOutSlowInEasing)
                ).plus(fadeIn(tween(300)))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it }, animationSpec = tween(300, easing = LinearOutSlowInEasing)
                ).plus(fadeOut(tween(300)))
            }
        ) {
            val viewModel: SearchNoteViewModel = hiltViewModel()
            SearchNoteScreen(
                onNavigateToEditNote = { id ->
                    navController.navigate(Screen.EditNote.getRouteWithArgs(id)) {
                        navController.currentDestination?.id?.let { id ->
                            popUpTo(id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
                state = viewModel.state,
                onEvent = viewModel::onEvent
            )
        }
    }
}