package com.felipeschoffen.todoapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.felipeschoffen.todoapplication.data.FakeData
import com.felipeschoffen.todoapplication.data.repository.TaskRepository
import com.felipeschoffen.todoapplication.ui.components.SearchTopBar
import com.felipeschoffen.todoapplication.ui.components.TasksList
import com.felipeschoffen.todoapplication.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val homeViewModel = HomeViewModel(TaskRepository())

    Scaffold(
        modifier = modifier,
        topBar = { SearchTopBar() }
    ) {
        Column(modifier = Modifier.padding(it)) {
            TasksList(
                tasks = homeViewModel.tasksList,
                onEdit = {

                },
                onCheckedChange = { id ->
                    homeViewModel.completeTask(id)
                },
                onClose = { task ->
                    homeViewModel.deleteTask(task)
                })
        }
    }
}