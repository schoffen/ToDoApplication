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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow
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

    private var _allTasksList = emptyFlow<List<Task>>()
    private var _filteredTasksList = MutableStateFlow<List<Task>>(emptyList())
    val filteredTasksList = _filteredTasksList.asStateFlow()

    init {
        viewModelScope.launch {
            _allTasksList = repository.getAllTasks()

            _allTasksList.collect { tasks ->
                _filteredTasksList.value = tasks
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

    fun onTaskEditClicked(task: Task) {
        sendBottomSheetShowRequest()
        _bottomSheetDefaults.value.bottomSheetTaskLabel = task.label
        _bottomSheetDefaults.value.bottomSheetOnSend = { label ->
            editTask(task.id, label)
            sendBottomSheetHideRequest()
        }
    }

    private fun editTask(taskId: Int, taskLabel: String) {
        viewModelScope.launch { repository.editTask(taskId, taskLabel) }
    }

    fun onAddTaskClicked() {
        sendBottomSheetShowRequest()
        _bottomSheetDefaults.value.bottomSheetTaskLabel = ""
        _bottomSheetDefaults.value.bottomSheetOnSend = { label ->
            insertTask(label)
            sendBottomSheetHideRequest()
        }
    }

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
        viewModelScope.launch { repository.completeTask(taskId) }
    }

    fun onFilterChange(prefix: String) {
        viewModelScope.launch {
            if (prefix.isEmpty()) {
                _allTasksList.collect { tasks ->
                    _filteredTasksList.value = tasks
                }
            }

            if (prefix.isNotEmpty()){
                _allTasksList.collect { tasks ->
                    _filteredTasksList.value =
                        tasks.filter { it.label.lowercase().contains(prefix.lowercase()) }
                }
            }
        }
    }
}