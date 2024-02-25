package com.simpleshop.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.simpleshop.api.AccountRepository
import com.simpleshop.api.OrderProduct
import com.simpleshop.api.RetrofitInstance
import com.simpleshop.database.ProductDB
import com.simpleshop.database.Repository
import com.simpleshop.database.ShopDatabase
import com.simpleshop.utils.SharedPreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailsViewModel(application: Application) : AndroidViewModel(application) {
    val repository: Repository
    val allProductsInCart: LiveData<List<ProductDB>>
    private val accountRepository = AccountRepository(RetrofitInstance.shopApi)
    private val sharedPreferencesHelper = SharedPreferencesHelper(application)

    init {
        val cartDao = ShopDatabase.getDatabase(application).dao()
        repository = Repository(cartDao)
        allProductsInCart = repository.allProductsInCart
    }

    fun insertProductIntoCart(product: ProductDB) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertProductIntoCart(product)
    }

    fun deleteProductFromCart(product: ProductDB) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteProductFromCart(product)
    }

    fun deleteProductFromCartById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteProductFromCartById(id)
    }

    // Method to submit order details with user email
    fun submitOrder(onOrderSubmitted: (Boolean) -> Unit) {
        viewModelScope.launch {
            // Retrieve the logged-in user's email
            val loggedInUserEmail = sharedPreferencesHelper.getLoggedInUser()?.email
            if (loggedInUserEmail == null) {
                onOrderSubmitted(false)
                return@launch
            }

            // Prepare the list of products for the order
            val productsInCart = allProductsInCart.value ?: emptyList()
            val orderProducts = productsInCart.map { productDB ->
                OrderProduct(productId = productDB.apiId ?: 0, quantity = productDB.quantity ?: 0)            }

            val response = accountRepository.submitOrderWithEmail(loggedInUserEmail, orderProducts)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    clearCart()
                    onOrderSubmitted(true)
                } else {
                    onOrderSubmitted(false)
                }
            }
        }
    }

    private fun clearCart() = viewModelScope.launch(Dispatchers.IO) {
        repository.clearCart()
    }

    fun getProductInCartByName(name: String): LiveData<ProductDB?> = liveData(Dispatchers.IO) {
        emit(repository.getProductInCartByName(name))
    }

}
