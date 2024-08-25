package com.felipeschoffen.todoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.felipeschoffen.todoapplication.data.FakeDatabase
import com.felipeschoffen.todoapplication.data.repository.TaskRepository
import com.felipeschoffen.todoapplication.ui.screens.HomeScreen
import com.felipeschoffen.todoapplication.ui.theme.ToDoApplicationTheme
import com.felipeschoffen.todoapplication.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val homeViewModel = HomeViewModel(repository = TaskRepository(FakeDatabase))

        enableEdgeToEdge()
        setContent {
            ToDoApplicationTheme {
                HomeScreen(homeViewModel)
            }
        }
    }
}
