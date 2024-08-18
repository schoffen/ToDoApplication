package com.felipeschoffen.todoapplication.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.felipeschoffen.todoapplication.ui.model.Task

@Composable
fun TasksList(
    tasks: List<Task>,
    onEdit: (Task) -> Unit,
    onCheckedChange: (Int) -> Unit,
    onClose: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items = tasks, key = { task -> task.id}) { task ->
            var completed by rememberSaveable {
                mutableStateOf(task.completed)
            }

            TaskItem(
                label = task.label,
                completed = completed,
                onEdit = { onEdit(task) },
                onCheckedChange = {
                    completed = !completed
                    onCheckedChange(task.id)
                },
                onClose = { onClose(task) })
        }
    }
}