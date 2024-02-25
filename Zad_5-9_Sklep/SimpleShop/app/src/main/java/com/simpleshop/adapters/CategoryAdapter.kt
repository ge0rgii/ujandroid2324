package com.simpleshop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.simpleshop.R
import com.simpleshop.databinding.CategoryItemBinding
import com.simpleshop.models.Category

class CategoryAdapter(private val onItemClicked: (Category) -> Unit) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var categoryList: List<Category> = ArrayList()

    // Update the list of categories and notify the adapter to refresh
    fun setCategoryList(categoryList: List<Category>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    // Provides a reference to the views for each data item
    class CategoryViewHolder(val binding: CategoryItemBinding, val onItemClicked: (Category) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.tvCategoryName.text = category.name
            Glide.with(binding.imgCategory.context)
                .load(category.imageUrl)
                .placeholder(R.drawable.ic_product)
                .into(binding.imgCategory)

            binding.root.setOnClickListener {
                onItemClicked(category)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, onItemClicked)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.bind(category)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = categoryList.size
}
