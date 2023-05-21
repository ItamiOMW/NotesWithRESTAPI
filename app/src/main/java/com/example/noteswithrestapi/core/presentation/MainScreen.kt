package com.example.noteswithrestapi.core.presentation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.noteswithrestapi.core.presentation.components.AppDrawerContent
import com.example.noteswithrestapi.core.presentation.navigation.NavigationItem
import com.example.noteswithrestapi.core.presentation.navigation.RootNavGraph
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
) {

    val navBackstackEntry = navController.currentBackStackEntryAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            navBackstackEntry.value?.destination?.parent?.route?.let { currentGraph ->
                AppDrawerContent(
                    items = listOf(
                        NavigationItem.NotesFeature,
                        NavigationItem.ProfileFeature
                    ),
                    currentRoute = currentGraph,
                    onItemClick = { navItem ->
                        scope.launch {
                            drawerState.close()
                        }
                        if (navItem.route != currentGraph) {
                            navController.navigate(navItem.route)
                        }
                    }
                )
            }
        },
        content = {
            RootNavGraph(
                navController = navController,
                onShowNavigationDrawer = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            )
        }
    )

}