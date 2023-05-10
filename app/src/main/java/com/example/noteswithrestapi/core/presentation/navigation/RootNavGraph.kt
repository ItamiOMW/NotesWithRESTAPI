package com.example.noteswithrestapi.core.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.noteswithrestapi.authentication_feature.presentation.authGraph
import com.example.noteswithrestapi.note_feature.presentation.noteGraph
import com.google.accompanist.navigation.animation.AnimatedNavHost


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    startGraphRoute: String = Graph.AUTH_GRAPH,
    navController: NavHostController,
) {

    AnimatedNavHost(
        modifier = modifier,
        navController = navController,
        route = Graph.ROOT,
        startDestination = startGraphRoute,
    ) {
        authGraph(navController)
        noteGraph(navController)
    }

}