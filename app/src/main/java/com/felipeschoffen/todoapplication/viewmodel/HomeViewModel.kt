package com.felipeschoffen.todoapplication.viewmodel

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.felipeschoffen.todoapplication.data.repository.TaskRepository
import com.felipeschoffen.todoapplication.data.model.Task
import java.io.Console

class HomeViewModel(private val repository: TaskRepository) : ViewModel() {
    val tasksList = repository.getAllTasks().toMutableStateList()

    fun insertTask(taskLabel: String) {
        if (taskLabel.isNotEmpty())
            repository.insertTask(taskLabel)
    }

    fun deleteTask(task: Task) {
        repository.deleteTask(task)
    }

    fun completeTask(taskId: Int) {
        repository.completeTask(taskId)
    }

    fun editTask(taskId: Int, taskLabel: String) {
        repository.editTask(taskId, taskLabel)
    }
}