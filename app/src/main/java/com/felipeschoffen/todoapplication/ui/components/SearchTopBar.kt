package com.felipeschoffen.todoapplication.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.felipeschoffen.todoapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    value: String,
    onTextChanged: (String) -> Unit,
    onClearClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                value = value,
                onValueChange = onTextChanged,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.search_field_placeholder),
                        fontStyle = FontStyle.Italic,
                        color = Color.LightGray
                    )
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = if (value.isNotEmpty()) MaterialTheme.colorScheme.primary else LocalContentColor.current.copy()
                    )
                },
                trailingIcon = {
                    if (value.isNotEmpty()) {
                        IconButton(onClick = onClearClicked) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                        }
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        })
}