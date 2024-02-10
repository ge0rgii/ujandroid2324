package com.simpletodos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// DAO (Data Access Object) for the Todo class
@Dao
interface TodoDao {
    @Insert
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("SELECT * FROM todo_table WHERE todoId = :key")
    fun get(key: Long): LiveData<Todo>

    @Query("SELECT * FROM todo_table ORDER BY todoId DESC")
    fun getAll(): LiveData<List<Todo>>

}