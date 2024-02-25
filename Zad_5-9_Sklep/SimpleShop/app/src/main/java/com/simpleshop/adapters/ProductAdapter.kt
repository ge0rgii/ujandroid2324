package com.simpleshop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.simpleshop.R
import com.simpleshop.databinding.ProductItemBinding
import com.simpleshop.models.Product

class ProductAdapter(private val onItemClicked: (Product) -> Unit) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var productList: List<Product> = listOf()

    fun setProductList(productList: List<Product>) {
        this.productList = productList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    class ProductViewHolder(private val binding: ProductItemBinding, private val onProductClick: (Product) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.tvProductName.text = product.name
            Glide.with(binding.imgProduct.context)
                .load(product.imageUrl)
                .placeholder(R.drawable.ic_product)
                .into(binding.imgProduct)

            binding.root.setOnClickListener { onProductClick(product) }
        }
    }
}
