package com.example.noteswithrestapi.note_feature.presentation.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.core.presentation.components.LoadingFailedComponent
import com.example.noteswithrestapi.core.presentation.components.pull_refresh.PullRefreshIndicator
import com.example.noteswithrestapi.core.presentation.components.pull_refresh.pullRefresh
import com.example.noteswithrestapi.core.presentation.components.pull_refresh.rememberPullRefreshState
import com.example.noteswithrestapi.core.presentation.theme.ui.theme.NotesWithRESTAPITheme
import com.example.noteswithrestapi.note_feature.domain.model.Note
import com.example.noteswithrestapi.note_feature.presentation.components.NoteComponent


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NotesScreen(
    onNavigateToAddNote: () -> Unit,
    onNavigateToEditNote: (id: Int) -> Unit,
    onNavigateToSearchNote: () -> Unit,
    onShowNavigationDrawer: () -> Unit,
    state: NotesState,
    onEvent: (NotesEvent) -> Unit,
) {

    val lazyListState = rememberLazyListState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = { onEvent(NotesEvent.OnRefresh) })

    LaunchedEffect(key1 = lazyListState.canScrollForward) {
        if (!lazyListState.canScrollForward && !state.isLoading && !state.endReached && state.errorMessage == null) {
            onEvent(NotesEvent.OnLoadNextItems)
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.title_notes),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
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
                },
                actions = {
                    IconButton(
                        onClick = onNavigateToSearchNote
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = stringResource(id = R.string.desc_search_icon),
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
                .fillMaxSize()
                .pullRefresh(pullRefreshState, enabled = !state.isLoading),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                state = lazyListState,
                contentPadding = PaddingValues(start = 10.dp, end = 10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp, bottom = 5.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
                items(state.notes, key = { note -> note.id }) { note ->
                    NoteComponent(
                        note = note,
                        onClick = {
                            onNavigateToEditNote(note.id)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement(tween(500))
                    )
                }
                if (state.isLoading) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                if (state.errorMessage != null) {
                    item {
                        LoadingFailedComponent(
                            errorMessage = state.errorMessage,
                            onRetryClick = { onEvent(NotesEvent.OnRetry) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = { state.isLoading },
                state = pullRefreshState,
                contentColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.align(Alignment.TopCenter)
            )
            AnimatedVisibility(
                visible = !lazyListState.isScrollInProgress,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 18.dp, bottom = 40.dp)
            ) {
                FloatingActionButton(
                    onClick = { onNavigateToAddNote() },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                    shape = MaterialTheme.shapes.small
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = stringResource(R.string.desc_add_note)
                    )
                }
            }
        }
    }


}

@Preview
@Composable
fun NotesScreenPreview() {
    NotesWithRESTAPITheme {
        NotesScreen(
            onNavigateToAddNote = {},
            onNavigateToEditNote = {},
            onNavigateToSearchNote = {},
            onShowNavigationDrawer = {},
            state = NotesState(
                notes = listOf(
                    Note(
                        0,
                        "Note Title",
                        "Hi there! Just wanted to leave a quick note to say that I hope you're having a great day. Remember to take breaks when you need them and stay focused. That's all i wanted to tell you",
                        date_updated = "April 30, 2023"
                    ),
                    Note(
                        1,
                        "Note Title",
                        "Hi there! Just wanted to leave a quick note to say that I hope you're having a great day. Remember to take breaks when you need them and stay focused. That's all i wanted to tell you",
                        date_updated = "April 30, 2023"
                    ),
                    Note(
                        2,
                        "Note Title",
                        "Hi there! Just wanted to leave a quick note to say that I hope you're having a great day. Remember to take breaks when you need them and stay focused. That's all i wanted to tell you",
                        date_updated = "April 30, 2023"
                    ),
                )
            ),
            onEvent = {}
        )
    }
}

