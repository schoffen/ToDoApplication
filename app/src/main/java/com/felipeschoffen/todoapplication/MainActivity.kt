package com.felipeschoffen.todoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.felipeschoffen.todoapplication.data.Database
import com.felipeschoffen.todoapplication.data.repository.TaskRepository
import com.felipeschoffen.todoapplication.ui.screens.HomeScreen
import com.felipeschoffen.todoapplication.ui.theme.ToDoApplicationTheme
import com.felipeschoffen.todoapplication.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            Database::class.java, "tasks_db"
        ).build()

        val taskDao = db.tasksDao()

        val homeViewModel = HomeViewModel(repository = TaskRepository(taskDao))

        enableEdgeToEdge()
        setContent {
            ToDoApplicationTheme {
                HomeScreen(homeViewModel)
            }
        }
    }
}
