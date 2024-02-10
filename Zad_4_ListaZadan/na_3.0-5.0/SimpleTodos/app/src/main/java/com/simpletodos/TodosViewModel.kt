package com.simpletodos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// implementation of ViewModel in the MVVM architecture
class TodosViewModel(val dao: TodoDao) : ViewModel(){
    var newTodoName = MutableLiveData<String>("")
    var newTodoDescription = MutableLiveData<String>("")
    val newTodoDeadline = MutableLiveData<String>("brak deadline'u")

    val todos = dao.getAll()

    fun addTodo(){
        viewModelScope.launch {
            val todo = Todo().apply {
                todoName = newTodoName.value ?: ""
                todoDescription = newTodoDescription.value ?: ""
                todoDeadline = newTodoDeadline.value ?: "brak deadline'u"
                clearFields()
            }

            dao.insert(todo)

        }

    }

    fun onDatePicked(year: Int, month: Int, dayOfMonth: Int) {
        val pickedDate = LocalDate.of(year, month, dayOfMonth)
        newTodoDeadline.value = pickedDate.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: "brak deadline'u"
    }

    fun clearFields() {
        newTodoName.value = ""
        newTodoDescription.value = ""
        newTodoDeadline.value = "brak deadline'u"
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            dao.delete(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            dao.update(todo)
        }
    }

}