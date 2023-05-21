package com.example.noteswithrestapi.note_feature.presentation.search_note

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.core.presentation.components.LoadingFailedComponent
import com.example.noteswithrestapi.core.presentation.components.pull_refresh.PullRefreshIndicator
import com.example.noteswithrestapi.core.presentation.components.pull_refresh.pullRefresh
import com.example.noteswithrestapi.core.presentation.components.pull_refresh.rememberPullRefreshState
import com.example.noteswithrestapi.core.presentation.theme.ui.theme.NotesWithRESTAPITheme
import com.example.noteswithrestapi.note_feature.presentation.components.NoteComponent


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchNoteScreen(
    onNavigateToEditNote: (noteId: Int) -> Unit,
    onNavigateBack: () -> Unit,
    state: SearchNoteState,
    onEvent: (SearchNoteEvent) -> Unit,
) {

    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val focusRequester = remember {
        FocusRequester()
    }

    val lazyListState = rememberLazyListState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoadingNextItems,
        onRefresh = { onEvent(SearchNoteEvent.OnRefresh) })

    LaunchedEffect(key1 = lazyListState.canScrollForward) {
        if (!lazyListState.canScrollForward && !state.isLoadingNextItems && !state.endReached && state.errorMessage == null) {
            onEvent(SearchNoteEvent.OnLoadNextItems)
        }
    }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        modifier = Modifier.focusRequester(focusRequester),
                        value = state.searchQueryInputValue,
                        onValueChange = { onEvent(SearchNoteEvent.OnSearchQueryValueChange(it)) },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedTrailingIconColor = Color.Transparent,
                            disabledTrailingIconColor = Color.Transparent,
                            textColor = MaterialTheme.colorScheme.primary,
                            placeholderColor = MaterialTheme.colorScheme.onBackground
                        ),
                        textStyle = MaterialTheme.typography.bodyLarge,
                        placeholder = {
                            Text(text = stringResource(id = R.string.hint_search_notes))
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(
                                id = R.string.desc_go_back
                            ),
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(SearchNoteEvent.OnClearQuery)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = stringResource(R.string.desc_clear_query_icon),
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .pullRefresh(pullRefreshState),
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
                items(state.searchItems, key = { note -> note.id }) { note ->
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
                if (state.isLoadingNextItems) {
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
                            onRetryClick = { onEvent(SearchNoteEvent.OnRetryLoadNextItems) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = { state.isLoadingNextItems },
                state = pullRefreshState,
                contentColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}


@Preview
@Composable
fun SearchNoteScreenPreview() {
    NotesWithRESTAPITheme() {
        SearchNoteScreen(
            onNavigateToEditNote = {},
            onNavigateBack = {},
            state = SearchNoteState(),
            onEvent = {}
        )
    }
}