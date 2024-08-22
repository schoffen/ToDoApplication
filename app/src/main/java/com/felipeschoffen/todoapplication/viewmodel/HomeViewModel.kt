package com.felipeschoffen.todoapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felipeschoffen.todoapplication.data.repository.TaskRepository
import com.felipeschoffen.todoapplication.data.model.Task
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: TaskRepository) : ViewModel() {
    sealed class HomeScreenEvent {
        data object ShowBottomSheetDialog : HomeScreenEvent()
        data object HideBottomSheetDialog : HomeScreenEvent()
    }

    private val _uiEvent = Channel<HomeScreenEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    data class HomeUiState(
        var taskList: List<Task>,
        var showBottomSheetDialog: Boolean,
        var bottomSheetTaskLabel: String,
        var bottomSheetOnSend: (String) -> Unit
    )

    private val _uiState = mutableStateOf(
        HomeUiState(
            taskList = repository.getAllTasks().toMutableStateList(),
            showBottomSheetDialog = false,
            bottomSheetTaskLabel = "",
            bottomSheetOnSend = {}
        )
    )

    val uiState by _uiState

    fun onBottomSheetOnDismissRequest() {
        sendBottomSheetHideRequest()
    }

    private fun sendBottomSheetShowRequest() {
        viewModelScope.launch { _uiEvent.send(HomeScreenEvent.ShowBottomSheetDialog) }
        _uiState.value = _uiState.value.copy(showBottomSheetDialog = true)
    }

    private fun sendBottomSheetHideRequest() {
        viewModelScope.launch { _uiEvent.send(HomeScreenEvent.HideBottomSheetDialog) }
        _uiState.value = _uiState.value.copy(showBottomSheetDialog = false)
    }

    fun onTaskEditClicked(task: Task) {
        sendBottomSheetShowRequest()
        _uiState.value.bottomSheetTaskLabel = task.label
        _uiState.value.bottomSheetOnSend = { label ->
            editTask(task.id, label)
            sendBottomSheetHideRequest()
        }
    }

    private fun editTask(taskId: Int, taskLabel: String) {
        repository.editTask(taskId, taskLabel)
    }

    fun onAddTaskClicked() {
        sendBottomSheetShowRequest()
        _uiState.value.bottomSheetTaskLabel = ""
        _uiState.value.bottomSheetOnSend = { label ->
            insertTask(label)
            sendBottomSheetHideRequest()
        }
    }

    private fun insertTask(taskLabel: String) {
        if (taskLabel.isNotEmpty())
            repository.insertTask(taskLabel)
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            delay(500)
            repository.deleteTask(task)
        }
    }

    fun completeTask(taskId: Int) {
        repository.completeTask(taskId)
    }
}