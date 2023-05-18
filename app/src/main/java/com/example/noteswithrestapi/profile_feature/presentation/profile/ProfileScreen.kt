package com.example.noteswithrestapi.profile_feature.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.core.presentation.components.ButtonComponent
import com.example.noteswithrestapi.core.presentation.components.LoadingFailedComponent
import com.example.noteswithrestapi.core.presentation.components.ProgressIndicatorComponent
import com.example.noteswithrestapi.core.presentation.theme.ui.theme.NotesWithRESTAPITheme
import com.example.noteswithrestapi.profile_feature.domain.model.user.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateToAuthentication: () -> Unit,
    onShowNavigationDrawer: () -> Unit,
    state: ProfileState,
    uiEvent: Flow<ProfileUiEvent>,
    onEvent: (ProfileEvent) -> Unit,
) {

    LaunchedEffect(key1 = Unit) {
        uiEvent.collect { event ->
            when (event) {
                ProfileUiEvent.OnLogoutComplete -> onNavigateToAuthentication()
            }
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.title_profile),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onShowNavigationDrawer
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = stringResource(id = R.string.desc_menu_icon),
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (state.errorMessage == null) {
                if (state.user != null) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.height(50.dp))
                        Text(
                            text = state.user.email,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stringResource(R.string.text_joined_at_with_param, state.user.date_created),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        ButtonComponent(
                            text = stringResource(R.string.title_logout),
                            enabled = { !state.isLoading },
                            leadingIcon = Icons.Rounded.Logout,
                            onButtonClick = {
                                onEvent(ProfileEvent.OnLogout)
                            }
                        )
                    }
                }
                ProgressIndicatorComponent(isLoading = { state.isLoading })
            } else {
                LoadingFailedComponent(
                    errorMessage = state.errorMessage,
                    onRetryClick = { onEvent(ProfileEvent.OnRetryLoadProfile) }
                )
            }
        }
    }

}


@Preview
@Composable
fun ProfileScreenPreview() {
    NotesWithRESTAPITheme {
        ProfileScreen(
            onNavigateToAuthentication = {},
            onShowNavigationDrawer = {},
            state = ProfileState(User("itamiomw@gmail.com", "May 18, 2023")),
            uiEvent = flow {},
            onEvent = {}
        )
    }
}