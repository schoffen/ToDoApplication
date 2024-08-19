package com.felipeschoffen.todoapplication.data.repository

import androidx.compose.runtime.toMutableStateList
import com.felipeschoffen.todoapplication.data.FakeData
import com.felipeschoffen.todoapplication.ui.model.Task

class TaskRepository() {
    private var _tasks = FakeData.getAllTasks()

    fun getAllTasks() = _tasks

    fun insertTask(task: Task) {

    }

    fun deleteTask(task: Task) {
        FakeData.removeTask(task)
    }

    fun completeTask(taskId: Int) {
        FakeData.completeTask(taskId)
    }
}