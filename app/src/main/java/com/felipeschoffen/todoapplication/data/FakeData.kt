package com.felipeschoffen.todoapplication.data

import androidx.compose.runtime.mutableStateListOf
import com.felipeschoffen.todoapplication.data.model.Database
import com.felipeschoffen.todoapplication.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Deprecated("Use FakeDatabase instead")
object FakeData {
    private val _taskList = mutableStateListOf(
        Task(id = 1, label = "Clean the house", completed = false),
        Task(id = 2, label = "Buy groceries", completed = false),
        Task(id = 3, label = "Walk the dog", completed = true),
        Task(id = 4, label = "Prepare lunch", completed = false),
        Task(id = 5, label = "Finish Android project", completed = true),
        Task(id = 6, label = "Read a book", completed = false),
        Task(id = 7, label = "Water the plants", completed = true),
        Task(id = 8, label = "Pay bills", completed = false),
        Task(id = 9, label = "Exercise for 30 minutes", completed = false),
        Task(id = 10, label = "Call parents", completed = true)
    )

    fun getAllTasks() = _taskList

    fun insertTask(taskLabel: String) {
        _taskList.add(
            Task(
                id = if (_taskList.isNotEmpty())_taskList.last().id + 1 else 1,
                label = taskLabel,
                completed = false
            )
        )
    }

    fun removeTask(task: Task) {
        _taskList.remove(task)
    }

    fun completeTask(taskId: Int) {
        _taskList.forEach { task ->
            if (task.id == taskId) {
                task.completed = !task.completed
            }
        }
    }

    fun editTask(taskId: Int, taskLabel: String) {
        _taskList.forEach { task ->
            if (task.id == taskId)
                task.label = taskLabel
        }
    }
}

object FakeDatabase : Database {

    private val _taskList = mutableStateListOf(
        Task(id = 1, label = "Clean the house", completed = false),
        Task(id = 2, label = "Buy groceries", completed = false),
        Task(id = 3, label = "Walk the dog", completed = true),
        Task(id = 4, label = "Prepare lunch", completed = false),
        Task(id = 5, label = "Finish Android project", completed = true),
        Task(id = 6, label = "Read a book", completed = false),
        Task(id = 7, label = "Water the plants", completed = true),
        Task(id = 8, label = "Pay bills", completed = false),
        Task(id = 9, label = "Exercise for 30 minutes", completed = false),
        Task(id = 10, label = "Call parents", completed = true)
    )

    override suspend fun getAllTasks(): Flow<List<Task>> {
        return flowOf(_taskList)
    }

    override suspend fun insertTask(taskLabel: String) {
        _taskList.add(
            Task(
                id = if (_taskList.isNotEmpty()) _taskList.last().id + 1 else 1,
                label = taskLabel,
                completed = false
            )
        )
    }

    override suspend fun removeTask(task: Task) {
        _taskList.remove(task)
    }

    override suspend fun completeTask(taskId: Int) {
        _taskList.forEach { task ->
            if (task.id == taskId) {
                task.completed = !task.completed
            }
        }
    }

    override suspend fun editTask(taskId: Int, taskLabel: String) {
        _taskList.forEach { task ->
            if (task.id == taskId)
                task.label = taskLabel
        }
    }

}