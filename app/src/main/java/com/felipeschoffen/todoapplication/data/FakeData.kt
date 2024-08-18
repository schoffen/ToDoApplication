package com.felipeschoffen.todoapplication.data

import com.felipeschoffen.todoapplication.ui.model.Task

object FakeData {
    private val _taskList = mutableListOf(
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

    val tasksList = _taskList

    fun insertTask(task: Task) {

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
}