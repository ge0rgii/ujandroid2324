package com.simpleshop

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.simpleshop.adapters.ProductAdapter
import com.simpleshop.databinding.ActivityProductsBinding
import com.simpleshop.viewModel.ProductsViewModel

class ProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsBinding
    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var adapter: ProductAdapter
    private var categoryId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Retrieve the category name and description from the intent
        val categoryName = intent.getStringExtra("CATEGORY_NAME")

        // Set the toolbar title and category description
        binding.toolbar.title = categoryName

        categoryId = intent.getIntExtra("CATEGORY_ID", 0)
        setupRecyclerView()
        observeProducts()

        viewModel.getProductsByCategory(categoryId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter { product ->
            // Create an intent to start ProductDetailsActivity
            val intent = Intent(this@ProductsActivity, ProductDetailsActivity::class.java).apply {
                // Put extra data into the intent for use in ProductDetailsActivity
                putExtra("PRODUCT_ID", product.id.toString())
                putExtra("PRODUCT_NAME", product.name)
                putExtra("PRODUCT_INFO", product.info)
                putExtra("PRODUCT_IMAGE_URL", product.imageUrl)
                putExtra("PRODUCT_PRICE", product.price.toString())

            }
            // Start ProductDetailsActivity
            startActivity(intent)
        }
        binding.productsRecyclerview.apply {
            layoutManager = GridLayoutManager(this@ProductsActivity, 1)
            adapter = this@ProductsActivity.adapter
        }
    }

    private fun observeProducts() {
        viewModel.observeProducts().observe(this) { products ->
            adapter.setProductList(products)
        }
    }
}

