package com.felipeschoffen.todoapplication.data.model

import kotlinx.coroutines.flow.Flow

interface Database {
    suspend fun getAllTasks(): Flow<List<Task>>

    suspend fun insertTask(taskLabel: String)

    suspend fun removeTask(task: Task)

    suspend fun completeTask(taskId: Int)

    suspend fun editTask(taskId: Int, taskLabel: String)
}