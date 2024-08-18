package com.felipeschoffen.todoapplication.ui.model

data class Task(
    val id: Int,
    val label: String,
    var completed: Boolean
)
