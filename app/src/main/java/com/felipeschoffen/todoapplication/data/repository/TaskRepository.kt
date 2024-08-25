package com.felipeschoffen.todoapplication.data.repository

import com.felipeschoffen.todoapplication.data.model.Database
import com.felipeschoffen.todoapplication.data.model.Task

class TaskRepository(private val db: Database) {

    suspend fun getAllTasks() = db.getAllTasks()

    suspend fun insertTask(taskLabel: String) {
        db.insertTask(taskLabel)
    }

    suspend fun deleteTask(task: Task) {
        db.removeTask(task)
    }

    suspend fun completeTask(taskId: Int) {
        db.completeTask(taskId)
    }

    suspend fun editTask(taskId: Int, taskLabel: String) {
        db.editTask(taskId, taskLabel)
    }
}