package com.felipeschoffen.todoapplication.data.repository

import androidx.compose.runtime.toMutableStateList
import com.felipeschoffen.todoapplication.data.FakeData
import com.felipeschoffen.todoapplication.ui.model.Task

class TaskRepository() {
    val tasks = getAllTasks().toMutableStateList()

    private fun getAllTasks(): List<Task> {
        return FakeData.tasksList
    }

    fun insertTask(task: Task) {

    }

    fun deleteTask(task: Task) {
        FakeData.removeTask(task)
    }

    fun completeTask(taskId: Int) {
        FakeData.completeTask(taskId)
    }
}