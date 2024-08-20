package com.felipeschoffen.todoapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.felipeschoffen.todoapplication.data.repository.TaskRepository
import com.felipeschoffen.todoapplication.ui.components.BottomSheetDialog
import com.felipeschoffen.todoapplication.ui.components.SearchTopBar
import com.felipeschoffen.todoapplication.ui.components.TasksList
import com.felipeschoffen.todoapplication.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import java.io.Console

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val homeViewModel = HomeViewModel(TaskRepository())

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheetDialog by remember { mutableStateOf(false) }

    var bottomSheetTaskLabel = ""
    var bottomSheetOnSend: (String) -> Unit = {}

    Scaffold(
        modifier = modifier,
        topBar = { SearchTopBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showBottomSheetDialog = true
                scope.launch { sheetState.show() }
                bottomSheetTaskLabel = ""
                bottomSheetOnSend = { label ->
                    homeViewModel.insertTask(label)

                    showBottomSheetDialog = false

                    scope.launch { sheetState.hide() }
                }
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {

            TasksList(
                tasks = homeViewModel.tasksList,
                onEdit = { task ->
                    showBottomSheetDialog = true

                    scope.launch { sheetState.show() }

                    bottomSheetTaskLabel = task.label

                    bottomSheetOnSend = { label ->
                            homeViewModel.editTask(task.id, label)
                            showBottomSheetDialog = false
                            scope.launch { sheetState.hide() }
                    }
                },
                onCheckedChange = { id ->
                    homeViewModel.completeTask(id)
                },
                onClose = { task ->
                    homeViewModel.deleteTask(task)
                })
        }

        if (showBottomSheetDialog) {
            BottomSheetDialog(
                sheetState = sheetState,
                taskLabel = bottomSheetTaskLabel,
                onSendClicked = bottomSheetOnSend,
                onDismissRequest = { showBottomSheetDialog = false })
        }
    }
}