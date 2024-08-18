package com.felipeschoffen.todoapplication.ui.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.felipeschoffen.todoapplication.data.repository.TaskRepository
import com.felipeschoffen.todoapplication.ui.model.Task

class HomeViewModel(private val repository: TaskRepository) : ViewModel() {
    val tasksList = repository.tasks.toMutableStateList()

    fun insertTask(task: Task) {
        repository.insertTask(task)
    }

    fun deleteTask(task: Task) {
        repository.deleteTask(task)
    }

    fun completeTask(taskId: Int) {
        repository.completeTask(taskId)
    }
}