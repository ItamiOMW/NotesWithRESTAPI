package com.example.noteswithrestapi.note_feature.presentation.edit_note

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.core.presentation.navigation.Screen
import com.example.noteswithrestapi.note_feature.domain.error.NoteError
import com.example.noteswithrestapi.note_feature.domain.model.Note
import com.example.noteswithrestapi.note_feature.domain.usecase.DeleteNoteUseCase
import com.example.noteswithrestapi.note_feature.domain.usecase.EditNoteUseCase
import com.example.noteswithrestapi.note_feature.domain.usecase.GetNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val application: Application,
    private val getNoteUseCase: GetNoteUseCase,
    private val editNoteUseCase: EditNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {


    private val _uiEvent = Channel<EditNoteUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(EditNoteState())
        private set

    private var note: Note? = null

    init {
        getNote()
    }


    fun onEvent(event: EditNoteEvent) {
        when (event) {
            is EditNoteEvent.OnContentInputValueChange -> {
                state = state.copy(contentInputValue = event.newValue)
            }

            is EditNoteEvent.OnTitleInputValueChange -> {
                state = state.copy(titleInputValue = event.newValue)
            }

            is EditNoteEvent.OnSaveClick -> {
                note?.id?.let { saveNote(state.titleInputValue, state.contentInputValue, it) }
            }

            is EditNoteEvent.OnDeleteClick -> {
                note?.id?.let { deleteNote(it) }
            }
        }
    }

    private fun saveNote(
        title: String,
        content: String,
        id: Int,
    ) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            when (val result = editNoteUseCase(title, content, id)) {
                is AppResponse.Success -> {
                    state = state.copy(isLoading = false)
                    _uiEvent.send(EditNoteUiEvent.OnNoteSaved)
                }

                is AppResponse.Failed -> {
                    state = state.copy(isLoading = false)
                    handleAppError(result.error)
                }
            }
        }
    }

    private fun deleteNote(id: Int) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            when (val result = deleteNoteUseCase(id)) {
                is AppResponse.Success -> {
                    state = state.copy(isLoading = false)
                    _uiEvent.send(EditNoteUiEvent.OnNoteDeleted)
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
                    _uiEvent.send(EditNoteUiEvent.OnShowSnackbar(application.getString(error.messageResId)))
                }

                is AppError.PoorNetworkConnectionError -> {
                    _uiEvent.send(EditNoteUiEvent.OnShowSnackbar(application.getString(R.string.error_poor_network_connection)))
                }

                is AppError.ServerError -> {
                    _uiEvent.send(EditNoteUiEvent.OnShowSnackbar(application.getString(R.string.error_server)))
                }

                is NoteError -> {
                    when (error) {
                        is NoteError.EmptyTitleError -> {
                            _uiEvent.send(EditNoteUiEvent.OnShowSnackbar(application.getString(R.string.error_empty_title)))
                        }

                        is NoteError.EmptyContentError -> {
                            _uiEvent.send(EditNoteUiEvent.OnShowSnackbar(application.getString(R.string.error_empty_content)))
                        }
                    }
                }

                else -> {
                    _uiEvent.send(EditNoteUiEvent.OnShowSnackbar(application.getString(R.string.error_unknown)))
                }
            }
        }

    }


    private fun getNote() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            savedStateHandle.get<Int>(Screen.NOTE_ID_ARG)?.let { noteId ->
                when (val result = getNoteUseCase(noteId)) {
                    is AppResponse.Success -> {
                        val noteResult = result.data
                        this@EditNoteViewModel.note = noteResult
                        state = state.copy(
                            titleInputValue = noteResult.title,
                            contentInputValue = noteResult.content,
                            isLoading = false
                        )
                    }

                    is AppResponse.Failed -> {
                        state = state.copy(isLoading = false)
                        when (result.error) {
                            is AppError.NotFoundError -> {
                                state = state.copy(
                                    getNoteErrorMessage = application.getString(R.string.error_not_found)
                                )
                            }

                            is AppError.PoorNetworkConnectionError -> {
                                state = state.copy(
                                    getNoteErrorMessage = application.getString(R.string.error_poor_network_connection)
                                )
                            }

                            else -> {
                                state = state.copy(
                                    getNoteErrorMessage = application.getString(R.string.error_unknown)
                                )
                            }
                        }
                    }
                }
            } ?: throw RuntimeException("Note Id wasn't passed")
        }
    }


}