package com.example.noteswithrestapi.core.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.noteswithrestapi.R

sealed class NavigationItem(
    @StringRes val nameId: Int,
    @StringRes val descId: Int,
    val route: String,
    val icon: ImageVector,
) {

    object NotesFeature: NavigationItem(
        nameId = R.string.title_notes,
        descId = R.string.desc_notes_screen,
        route = Graph.NOTE_GRAPH,
        icon = Icons.Rounded.Notes
    )

    object ProfileFeature: NavigationItem(
        nameId = R.string.title_profile,
        descId = R.string.desc_profile_screen,
        route = Graph.PROFILE_GRAPH,
        icon = Icons.Rounded.AccountCircle
    )

}
