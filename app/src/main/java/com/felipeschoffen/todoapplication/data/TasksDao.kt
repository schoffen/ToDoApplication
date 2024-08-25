package com.felipeschoffen.todoapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.felipeschoffen.todoapplication.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>

    @Insert
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun removeTask(task: Task)

    @Query("UPDATE tasks SET completed = NOT completed WHERE id = :taskId")
    suspend fun completeTask(taskId: Int)

    @Query("UPDATE tasks SET label = :taskLabel WHERE id = :taskId")
    suspend fun editTask(taskId: Int, taskLabel: String)
}