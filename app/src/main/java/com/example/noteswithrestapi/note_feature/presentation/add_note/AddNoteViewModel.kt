package com.example.noteswithrestapi.note_feature.presentation.add_note

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.note_feature.domain.error.NoteError
import com.example.noteswithrestapi.note_feature.domain.usecase.AddNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val application: Application,
) : ViewModel() {


    private val _uiEvent = Channel<AddNoteUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(AddNoteState())
        private set


    fun onEvent(event: AddNoteEvent) {
        when (event) {
            is AddNoteEvent.OnContentInputValueChange -> {
                state = state.copy(contentInputValue = event.newValue)
            }

            is AddNoteEvent.OnTitleInputValueChange -> {
                state = state.copy(titleInputValue = event.newValue)
            }

            is AddNoteEvent.OnSaveClick -> {
                saveNote(state.titleInputValue, state.contentInputValue)
            }

        }
    }

    private fun saveNote(
        title: String,
        content: String,
    ) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            when (val result = addNoteUseCase(title, content)) {
                is AppResponse.Success -> {
                    state = state.copy(isLoading = false)
                    _uiEvent.send(AddNoteUiEvent.OnNoteSaved)
                }

                is AppResponse.Failed -> {
                    state = state.copy(isLoading = false)
                    handleAppError(result.error)
                }
            }
        }
    }


    private fun handleAppError(error: AppError) {
        viewModelScope.launch {
            when (error) {
                is AppError.GeneralError -> {
                    _uiEvent.send(AddNoteUiEvent.OnShowSnackbar(application.getString(error.messageResId)))
                }

                is AppError.PoorNetworkConnectionError -> {
                    _uiEvent.send(AddNoteUiEvent.OnShowSnackbar(application.getString(R.string.error_poor_network_connection)))
                }

                is AppError.ServerError -> {
                    _uiEvent.send(AddNoteUiEvent.OnShowSnackbar(application.getString(R.string.error_server)))
                }

                is NoteError -> {
                    when (error) {
                        is NoteError.EmptyTitleError -> {
                            _uiEvent.send(AddNoteUiEvent.OnShowSnackbar(application.getString(R.string.error_empty_title)))
                        }

                        is NoteError.EmptyContentError -> {
                            _uiEvent.send(AddNoteUiEvent.OnShowSnackbar(application.getString(R.string.error_empty_content)))
                        }
                    }
                }

                else -> {
                    _uiEvent.send(AddNoteUiEvent.OnShowSnackbar(application.getString(R.string.error_unknown)))
                }
            }
        }

    }


}