package com.felipeschoffen.todoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.felipeschoffen.todoapplication.ui.screens.HomeScreen
import com.felipeschoffen.todoapplication.ui.theme.ToDoApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoApplicationTheme {
                HomeScreen()
            }
        }
    }
}
