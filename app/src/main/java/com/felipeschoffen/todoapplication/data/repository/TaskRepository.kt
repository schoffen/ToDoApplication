package com.felipeschoffen.todoapplication.data.repository

import com.felipeschoffen.todoapplication.data.TasksDao
import com.felipeschoffen.todoapplication.data.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val db: TasksDao) {

    fun getAllTasks(): Flow<List<Task>> = db.getAllTasks()

    suspend fun insertTask(task: Task) {
        db.insertTask(task)
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