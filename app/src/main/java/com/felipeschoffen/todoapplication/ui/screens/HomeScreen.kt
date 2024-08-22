package com.felipeschoffen.todoapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.felipeschoffen.todoapplication.data.repository.TaskRepository
import com.felipeschoffen.todoapplication.ui.components.BottomSheetDialog
import com.felipeschoffen.todoapplication.ui.components.SearchTopBar
import com.felipeschoffen.todoapplication.ui.components.TasksList
import com.felipeschoffen.todoapplication.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val homeViewModel = HomeViewModel(TaskRepository())

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = homeViewModel) {

        // Handle when sheetState should show or hide the bottom sheet dialog from the viewmodel
        homeViewModel.uiEvent.collect { event ->
            when (event) {
                HomeViewModel.HomeScreenEvent.ShowBottomSheetDialog -> {
                    scope.launch { sheetState.show() }
                }

                HomeViewModel.HomeScreenEvent.HideBottomSheetDialog -> {
                    scope.launch { sheetState.hide() }
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = { SearchTopBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                homeViewModel.onAddTaskClicked()
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            TasksList(
                tasks = homeViewModel.uiState.taskList,
                onEdit = { task ->
                    homeViewModel.onTaskEditClicked(task)
                },
                onCheckedChange = { id ->
                    homeViewModel.completeTask(id)
                },
                onClose = { task ->
                    homeViewModel.deleteTask(task)
                })
        }

        if (homeViewModel.uiState.showBottomSheetDialog) {
            BottomSheetDialog(
                sheetState = sheetState,
                taskLabel = homeViewModel.uiState.bottomSheetTaskLabel,
                onSendClicked = homeViewModel.uiState.bottomSheetOnSend,
                onDismissRequest = { homeViewModel.onBottomSheetOnDismissRequest() })
        }
    }
}