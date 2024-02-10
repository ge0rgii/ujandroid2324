package com.simpletodos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// creates TodosViewModel (takes TodoDao as a parameter)
class TodosViewModelFactory(private val dao: TodoDao) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodosViewModel::class.java)) {
            return TodosViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
