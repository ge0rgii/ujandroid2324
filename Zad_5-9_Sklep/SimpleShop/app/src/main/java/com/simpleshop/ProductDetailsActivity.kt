package com.simpleshop

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.simpleshop.database.ProductDB
import com.simpleshop.databinding.ActivityProductDetailsBinding
import com.simpleshop.viewModel.ProductDetailsViewModel

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var viewModel: ProductDetailsViewModel
    private var productName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ProductDetailsViewModel::class.java]

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        manageCollapsingToolbar()

        productName = intent.getStringExtra("PRODUCT_NAME")
        val productInfo = intent.getStringExtra("PRODUCT_INFO")
        val productImageUrl = intent.getStringExtra("PRODUCT_IMAGE_URL")
        val productPrice = intent.getStringExtra("PRODUCT_PRICE")
        val productId = intent.getStringExtra("PRODUCT_ID")

//        binding.tvProductName.text = productName
        binding.collapsingToolbar.title = productName
        binding.tvProductInfo.text = productInfo
        binding.tvProductPrice.text = getString(R.string.product_price_format, productPrice)

        Glide.with(this)
            .load(productImageUrl)
            .placeholder(R.drawable.ic_product)
            .into(binding.imgProductDetail)

        // Initially check if the product is in the cart
        checkProductInCart()

        binding.fabAddToCart.setOnClickListener {
            handleProductCartAction(productName, productInfo, productImageUrl, productPrice, productId)
        }
    }

    private fun checkProductInCart() {
        productName?.let { name ->
            viewModel.getProductInCartByName(name).observe(this, Observer { product ->
                updateFabIcon(product != null)
            })
        }
    }

    private fun handleProductCartAction(productName: String?, productInfo: String?, productImageUrl: String?, productPrice: String?, productId: String? = null) {
        productName?.let { name ->
            viewModel.getProductInCartByName(name).observe(this, Observer { product ->
                if (product != null) {
                    // Product is in cart, proceed to remove
                    viewModel.deleteProductFromCart(product)
                    Toast.makeText(this, "$name removed from cart", Toast.LENGTH_SHORT).show()
                } else {
                    // Product is not in cart, proceed to add
                    val newProduct = ProductDB(
                        productName = name,
                        productInfo = productInfo,
                        productImageUrl = productImageUrl,
                        productPrice = productPrice?.toDouble(),
                        quantity = 1,
                        apiId = productId?.toInt()
                    )
                    viewModel.insertProductIntoCart(newProduct)
                    Toast.makeText(this, "$name added to cart", Toast.LENGTH_SHORT).show()
                }
                // Re-check cart status after operation
                checkProductInCart()
            })
        }
    }

    private fun updateFabIcon(isInCart: Boolean) {
        binding.fabAddToCart.setImageResource(
            if (isInCart) R.drawable.ic_remove_shopping_cart else R.drawable.ic_add_shopping_cart
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun manageCollapsingToolbar() {
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            if (Math.abs(verticalOffset) - binding.appBar.totalScrollRange == 0) {
                // Collapsed
                binding.collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.colorOnPrimary))
            } else {
                // Expanded
                binding.collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, R.color.colorPrimaryVariant))
            }
        })
    }
}