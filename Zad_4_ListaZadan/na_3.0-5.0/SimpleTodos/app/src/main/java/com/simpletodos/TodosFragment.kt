package com.simpletodos

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.simpletodos.databinding.FragmentTodosBinding
import java.time.LocalDate

// main fragment of the app
class TodosFragment : Fragment(), TodoItemAdapter.OnItemCheckChanged {
    private var _binding: FragmentTodosBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TodosViewModel

    override fun onItemCheckChanged(todo: Todo, isChecked: Boolean) {
        val updatedTodo = todo.copy(todoDone = isChecked)
        viewModel.updateTodo(updatedTodo)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTodosBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this,
            TodosViewModelFactory(TodoDatabase.getInstance(requireNotNull(this.activity).application).todoDao)).get(TodosViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = TodoItemAdapter(this)
        binding.todosList.adapter = adapter

        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = adapter.getTodoAt(position)

                viewModel.deleteTodo(item)

            }
        }

        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.todosList)

        viewModel.todos.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.todoDeadline.apply {
            isFocusable = false
            setOnClickListener {
                val current = LocalDate.now()
                val datePickerDialog = DatePickerDialog(context, { _, year, month, dayOfMonth ->
                    viewModel.onDatePicked(year, month + 1, dayOfMonth)
                }, current.year, current.monthValue - 1, current.dayOfMonth)

                datePickerDialog.show()
            }
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

