package com.felipeschoffen.todoapplication.ui.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.felipeschoffen.todoapplication.data.model.Task

@OptIn(ExperimentalFoundationApi::class)
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
        items(items = tasks, key = { task -> task.id }) { task ->

            var completed by rememberSaveable {
                mutableStateOf(task.completed)
            }

            var visible by remember {
                mutableStateOf(true)
            }

            AnimatedVisibility(
                visible = visible,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                TaskItem(
                    modifier = Modifier.animateItemPlacement(),
                    label = task.label,
                    completed = completed,
                    onEdit = { onEdit(task) },
                    onCheckedChange = {
                        completed = !completed
                        onCheckedChange(task.id)
                    },
                    onClose = {
                        visible = false
                        onClose(task)
                    })
            }

        }
    }
}