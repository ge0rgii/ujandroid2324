package com.simpleshop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.simpleshop.R
import com.simpleshop.database.ProductDB
import com.simpleshop.database.Repository
import com.simpleshop.databinding.ProductCartItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductCartAdapter(private val repository: Repository) :
    RecyclerView.Adapter<ProductCartAdapter.ProductCartViewHolder>() {
    private var productsInCart: List<ProductDB> = ArrayList()

    fun setProductsList(products: List<ProductDB>) {
        this.productsInCart = products
        notifyDataSetChanged()
    }

    class ProductCartViewHolder(val binding: ProductCartItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCartViewHolder {
        return ProductCartViewHolder(ProductCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductCartViewHolder, position: Int) {
        val product = productsInCart[position]
        holder.binding.apply {
            tvProductName.text = product.productName
            // format price to show in zloty
            tvProductPrice.text = "Cena: ${product.productPrice} zł"
            tvProductQuantity.text = "Liczba: ${product.quantity}"

            Glide.with(holder.itemView)
                .load(product.productImageUrl)
                .placeholder(R.drawable.ic_product)
                .into(imgProduct)

            btnPlus.setOnClickListener {
                adjustProductQuantity(product, true)
            }

            btnMinus.setOnClickListener {
                adjustProductQuantity(product, false)
            }
        }
    }

    private fun adjustProductQuantity(product: ProductDB, isAddition: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            product.quantity?.let {
                val newQuantity: Int = if (isAddition) it + 1 else it - 1
                if (newQuantity > 0) {
                    repository.updateProductInCart(product.copy(quantity = newQuantity))
                } else {
                    repository.deleteProductFromCart(product)
                }
            }
            // Trigger UI update
            CoroutineScope(Dispatchers.Main).launch {
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = productsInCart.size
}
