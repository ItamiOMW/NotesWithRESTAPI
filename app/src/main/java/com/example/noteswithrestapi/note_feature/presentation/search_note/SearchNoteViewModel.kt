package com.example.noteswithrestapi.note_feature.presentation.search_note

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.core.domain.paginator.DefaultPaginator
import com.example.noteswithrestapi.note_feature.domain.usecase.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNoteViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val application: Application,
) : ViewModel() {


    var state by mutableStateOf(SearchNoteState())
        private set


    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = { state = state.copy(isLoadingNextItems = it) },
        onRequest = { nextPage -> getNotesUseCase.invoke(nextPage, query) },
        getNextKey = { state.page + 1 },
        onError = { appError -> handleAppError(appError) },
        onSuccess = { items, newKey ->
            state = state.copy(
                searchItems = state.searchItems + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    private var query: String = ""

    private var searchQueryJob: Job? = null


    fun onEvent(event: SearchNoteEvent) {
        when (event) {
            is SearchNoteEvent.OnClearQuery -> {
                state = state.copy(searchQueryInputValue = "")
                query = ""
                refresh()
            }

            is SearchNoteEvent.OnLoadNextItems -> {
                loadNextItems()
            }

            is SearchNoteEvent.OnRetryLoadNextItems -> {
                retryLoadNextItems()
            }

            is SearchNoteEvent.OnSearchQueryValueChange -> {
                state = state.copy(searchQueryInputValue = event.newValue)
                searchQueryJob?.cancel()
                searchQueryJob = viewModelScope.launch {
                    delay(1000L)
                    query = state.searchQueryInputValue
                    refresh()
                }
            }

            is SearchNoteEvent.OnRefresh -> {
                refresh()
            }
        }
    }

    private fun retryLoadNextItems() {
        viewModelScope.launch {
            state = state.copy(errorMessage = null)
            paginator.loadNextPage()
        }
    }

    private fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextPage()
        }
    }


    private fun refresh() {
        viewModelScope.launch {
            state = state.copy(searchItems = emptyList(), endReached = false, page = 1, errorMessage = null)
            paginator.reset()
            paginator.loadNextPage()
        }
    }


    private fun handleAppError(error: AppError) {

        when (error) {
            is AppError.GeneralError -> {
                state = state.copy(errorMessage = application.getString(error.messageResId))
            }

            is AppError.PoorNetworkConnectionError -> {
                state =
                    state.copy(errorMessage = application.getString(R.string.error_poor_network_connection))
            }

            is AppError.ServerError -> {
                state = state.copy(errorMessage = application.getString(R.string.error_unknown))
            }

            is AppError.NotFoundError -> {
                state = state.copy(endReached = true)
            }

            else -> {
                state = state.copy(
                    errorMessage = application.getString(R.string.error_unknown),
                )
                Log.e("UNEXPECTED_ERROR", error.toString())
            }
        }
    }

}