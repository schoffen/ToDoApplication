package com.felipeschoffen.todoapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.felipeschoffen.todoapplication.data.model.Task

@Database(entities = [Task::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun tasksDao(): TasksDao
}