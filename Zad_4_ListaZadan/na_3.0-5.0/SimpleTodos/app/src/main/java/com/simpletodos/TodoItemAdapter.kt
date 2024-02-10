package com.simpletodos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.simpletodos.databinding.TodoItemBinding

// adapter for the Todo list
class TodoItemAdapter(private val onItemCheckChanged: OnItemCheckChanged) : ListAdapter<Todo, TodoItemAdapter.TodoItemViewHolder>(TodoDiffItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : TodoItemViewHolder = TodoItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.binding.todoDone.setOnCheckedChangeListener { _, isChecked ->
            onItemCheckChanged.onItemCheckChanged(item, isChecked)
        }

        holder.binding.executePendingBindings()

    }

    interface OnItemCheckChanged {
        fun onItemCheckChanged(todo: Todo, isChecked: Boolean)
    }

    fun getTodoAt(position: Int): Todo = getItem(position)

    class TodoItemViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): TodoItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TodoItemBinding.inflate(layoutInflater, parent, false)
                return TodoItemViewHolder(binding)
            }
        }

        fun bind(item: Todo) {
            binding.todo = item
        }

    }

}
