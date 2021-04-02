package com.monusk.todo.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.monusk.todo.data.models.ToDoData

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDoData)

    @Update
    suspend fun updateData(toDoData: ToDoData)

    @Delete
    suspend fun deleteItem(toDoData: ToDoData)

    @Query("Delete from todo_table")
    suspend fun deleteAll()

    @Query("Select * from todo_table where title like :searchQuery")
    fun searchDatabase(searchQuery: String) : LiveData<List<ToDoData>>

    @Query("Select * from todo_table order by case when priority like 'H%' then 1 when priority like 'M%' then 2 when priority like 'L%' then 3 end")
    fun sortByHighPriority() : LiveData<List<ToDoData>>

    @Query("Select * from todo_table order by case when priority like 'L%' then 1 when priority like 'M%' then 2 when priority like 'H%' then 3 end")
    fun sortByLowPriority() : LiveData<List<ToDoData>>


}