package com.felipeschoffen.todoapplication.ui.components

import android.graphics.Color
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TaskItem(
    label: String,
    completed: Boolean,
    onEdit: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onEdit) {
            Icon(imageVector = Icons.Filled.Edit, contentDescription = null)
        }
        Text(text = label, modifier = Modifier.weight(1f))
        Checkbox(checked = completed, onCheckedChange = onCheckedChange)
        IconButton(onClick = onClose) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = null)
        }
    }
}

@Preview
@Composable
fun PreviewItem(modifier: Modifier = Modifier) {
    var completed by remember {
        mutableStateOf(false)
    }

    TaskItem(
        label = "Clean the house",
        completed = completed,
        onEdit = {},
        onClose = {},
        onCheckedChange = {
            completed = !completed
        }
    )
}