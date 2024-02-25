package com.simpleshop.api

import com.simpleshop.models.Category
import com.simpleshop.models.Product
import com.simpleshop.models.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ShopApi {
    @GET("categories")
    fun getCategories(): Call<List<Category>>

    @GET("categories/{id}")
    fun getCategoryById(@Path("id") id: Int): Call<Category>

    @GET("products")
    fun getAllProducts(): Call<List<Product>>

    @GET("products/{id}")
    fun getProductById(@Path("id") id: Int): Call<Product>

    @GET("categories/{categoryId}/products")
    fun getProductsByCategoryId(@Path("categoryId") categoryId: Int): Call<List<Product>>

    @POST("register")
    suspend fun registerUser(@Body userRequest: UserRequest): Response<User>

    @POST("login")
    suspend fun loginUser(@Body userLoginRequest: UserLoginRequest): Response<User>

    @POST("update-profile")
    suspend fun updateUserProfile(@Body userUpdateRequest: UserUpdateRequest): Response<User>

    // Updated method to submit an order using email
    @POST("submit-order")
    suspend fun submitOrderWithEmail(@Body orderWithEmailRequest: OrderWithEmailRequest): Response<Void> // Use Void for responses without a body

}
