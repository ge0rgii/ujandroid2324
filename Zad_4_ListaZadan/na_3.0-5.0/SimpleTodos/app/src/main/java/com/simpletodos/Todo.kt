package com.simpletodos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity class for the Todo table
@Entity(tableName = "todo_table")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    var todoId: Long = 0L,

    @ColumnInfo(name = "todo_name")
    var todoName: String = "",

    @ColumnInfo(name = "todo_description")
    var todoDescription: String = "",

    @ColumnInfo(name = "todo_deadline")
    var todoDeadline: String = "brak deadline'u",

    @ColumnInfo(name = "todo_done")
    var todoDone: Boolean = false

)