package com.simpleshop.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class Repository(private val dao: Dao) {

    val allProductsInCart: LiveData<List<ProductDB>> = dao.getAllProductsInCart()

    @WorkerThread
    suspend fun insertProductIntoCart(product: ProductDB) {
        dao.insertProductIntoCart(product)
    }

    @WorkerThread
    suspend fun deleteProductFromCart(product: ProductDB) {
        dao.deleteProductFromCart(product)
    }

    @WorkerThread
    suspend fun deleteProductFromCartById(id: Int) {
        dao.deleteProductFromCartById(id)
    }

    @WorkerThread
    fun getProductInCartByName(name: String): ProductDB? {
        return dao.getProductInCartByName(name)
    }

    @WorkerThread
    suspend fun updateProductInCart(product: ProductDB) {
        dao.updateProductInCart(product)
    }

    // clear all products from the cart
    @WorkerThread
    suspend fun clearCart() {
        dao.clearCart()
    }
}
