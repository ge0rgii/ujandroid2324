package com.simpleshop.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductIntoCart(product: ProductDB)

    @Delete
    fun deleteProductFromCart(product: ProductDB)

    // delete by id
    @Query("DELETE FROM cart WHERE id = :id")
    fun deleteProductFromCartById(id: Int)

    // Get all products from the cart as a live data
    @Query("SELECT * FROM cart")
    fun getAllProductsInCart(): LiveData<List<ProductDB>>

    // check if the product is already in the cart
    @Query("SELECT * FROM cart WHERE productName = :productName")
    fun getProductInCartByName(productName: String): ProductDB?

    @Update
    fun updateProductInCart(product: ProductDB)

    @Query("DELETE FROM cart")
    fun clearCart()

}
