package com.example.noteswithrestapi.profile_feature.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.noteswithrestapi.core.presentation.navigation.Graph
import com.example.noteswithrestapi.core.presentation.navigation.Screen
import com.example.noteswithrestapi.profile_feature.presentation.profile.ProfileScreen
import com.example.noteswithrestapi.profile_feature.presentation.profile.ProfileViewModel
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.profileGraph(
    navController: NavController,
    onShowNavigationDrawer: () -> Unit,
) {
    navigation(
        route = Graph.PROFILE_GRAPH,
        startDestination = Screen.Profile.fullRoute
    ) {
        composable(Screen.Profile.fullRoute) {
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                onNavigateToAuthentication = {
                    navController.navigate(Graph.AUTH_GRAPH) {
                        navController.currentDestination?.id?.let { id ->
                            this.popUpTo(id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                    navController.graph.setStartDestination(Graph.AUTH_GRAPH)
                },
                onShowNavigationDrawer = onShowNavigationDrawer,
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
    }
}