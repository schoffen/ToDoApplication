package com.felipeschoffen.todoapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felipeschoffen.todoapplication.data.model.Task
import com.felipeschoffen.todoapplication.data.repository.TaskRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: TaskRepository) : ViewModel() {
    sealed class HomeScreenEvent {
        data object ShowBottomSheetDialog : HomeScreenEvent()
        data object HideBottomSheetDialog : HomeScreenEvent()
    }

    private val _uiEvent = Channel<HomeScreenEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    data class BottomSheetDefaults(
        var showBottomSheetDialog: Boolean = false,
        var bottomSheetTaskLabel: String = "",
        var bottomSheetOnSend: (String) -> Unit = {}
    )

    private val _bottomSheetDefaults = mutableStateOf(BottomSheetDefaults())
    val bottomSheetDefaults by _bottomSheetDefaults

    private var _filterPrefix = MutableStateFlow("")
    val filterPrefix = _filterPrefix.asStateFlow()

    private var _allTasksList = MutableStateFlow<List<Task>>(emptyList())
    val tasks = _filterPrefix.combine(_allTasksList) { prefix, tasks ->
        if (prefix.isBlank())
            tasks
        else {
            tasks.filter { task -> task.label.lowercase().contains(prefix.lowercase()) }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _allTasksList.value
    )

    init {
        viewModelScope.launch {
            repository.getAllTasks().collect {
                _allTasksList.value = it
            }
        }
    }

    fun onBottomSheetOnDismissRequest() {
        sendBottomSheetHideRequest()
    }

    private fun sendBottomSheetShowRequest() {
        viewModelScope.launch { _uiEvent.send(HomeScreenEvent.ShowBottomSheetDialog) }
        _bottomSheetDefaults.value = _bottomSheetDefaults.value.copy(showBottomSheetDialog = true)
    }

    private fun sendBottomSheetHideRequest() {
        viewModelScope.launch { _uiEvent.send(HomeScreenEvent.HideBottomSheetDialog) }
        _bottomSheetDefaults.value = _bottomSheetDefaults.value.copy(showBottomSheetDialog = false)
    }

    // Callbacks

    fun onTaskEditClicked(task: Task) {
        sendBottomSheetShowRequest()
        _bottomSheetDefaults.value.bottomSheetTaskLabel = task.label
        _bottomSheetDefaults.value.bottomSheetOnSend = { label ->
            editTask(task.id, label)
            sendBottomSheetHideRequest()
        }
    }

    fun onAddTaskClicked() {
        sendBottomSheetShowRequest()
        _bottomSheetDefaults.value.bottomSheetTaskLabel = ""
        _bottomSheetDefaults.value.bottomSheetOnSend = { label ->
            insertTask(label)
            sendBottomSheetHideRequest()
        }
    }

    fun onSearchTextChanged(text: String) {
        _filterPrefix.value = text
    }

    fun onSearchClear() {
        _filterPrefix.value = ""
    }

    // CRUD Functions

    private fun insertTask(taskLabel: String) {
        if (taskLabel.isNotEmpty())
            viewModelScope.launch { repository.insertTask(Task(label = taskLabel)) }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            delay(500)
            repository.deleteTask(task)
        }
    }

    fun completeTask(taskId: Int) {
        viewModelScope.launch {
            repository.completeTask(taskId)
        }
    }

    private fun editTask(taskId: Int, taskLabel: String) {
        viewModelScope.launch { repository.editTask(taskId, taskLabel) }
    }
}