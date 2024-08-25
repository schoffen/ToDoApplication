package com.felipeschoffen.todoapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var label: String = "",
    var completed: Boolean = false
)
