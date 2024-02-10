package com.simpletodos

import androidx.recyclerview.widget.DiffUtil

// makes the Todo list adapter more efficient
class TodoDiffItemCallback : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo) = (oldItem.todoId == newItem.todoId)
    override fun areContentsTheSame(oldItem: Todo, newItem: Todo) = (oldItem == newItem)
}