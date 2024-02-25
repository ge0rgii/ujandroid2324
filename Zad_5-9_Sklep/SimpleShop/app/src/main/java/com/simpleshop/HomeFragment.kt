package com.simpleshop

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.simpleshop.adapters.CategoryAdapter
import com.simpleshop.databinding.FragmentHomeBinding
import com.simpleshop.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Using the viewModels() delegate to obtain a ViewModel instance
    private val homeViewModel: HomeViewModel by viewModels()

    // Initialize the CategoryAdapter with the click listener
    private val categoryAdapter by lazy {
        CategoryAdapter { category ->
            val intent = Intent(context, ProductsActivity::class.java).apply {
                putExtra("CATEGORY_ID", category.id)
                putExtra("CATEGORY_NAME", category.name)
            }
            startActivity(intent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeCategories()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = categoryAdapter
        }
    }

    private fun observeCategories() {
        homeViewModel.observeCategories().observe(viewLifecycleOwner) { categories ->
            categoryAdapter.setCategoryList(categories)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
