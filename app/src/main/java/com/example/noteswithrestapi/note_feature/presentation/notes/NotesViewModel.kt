package com.example.noteswithrestapi.note_feature.presentation.notes

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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val application: Application,
) : ViewModel() {


    var state by mutableStateOf(NotesState())
        private set


    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = { state = state.copy(isLoading = it) },
        onRequest = { nextPage -> getNotesUseCase.invoke(nextPage) },
        getNextKey = { state.page + 1 },
        onError = { appError -> handleAppError(appError) },
        onSuccess = { items, newKey ->
            state = state.copy(
                notes = state.notes + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    init {
        loadNextItems()
    }


    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.OnLoadNextItems -> {
                loadNextItems()
            }

            is NotesEvent.OnRefresh -> {
                refresh()
            }

            is NotesEvent.OnRetry -> {
               onRetryLoadNextItems()
            }
        }
    }

    private fun onRetryLoadNextItems() {
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
            state = state.copy(notes = emptyList(), page = 1, endReached = false, errorMessage = null)
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
                state = state.copy(errorMessage = application.getString(R.string.error_poor_network_connection))
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