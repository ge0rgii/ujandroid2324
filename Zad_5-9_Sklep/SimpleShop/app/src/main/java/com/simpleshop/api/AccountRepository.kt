package com.simpleshop.api

import com.simpleshop.models.User
import retrofit2.Response

class AccountRepository(private val api: ShopApi) {

    suspend fun registerUser(email: String, password: String): Response<User> {
        return api.registerUser(UserRequest(email, password))
    }

    suspend fun loginUser(email: String, password: String): Response<User> {
        return api.loginUser(UserLoginRequest(email, password))
    }

    suspend fun updateUserProfile(email: String, newPassword: String, firstName: String, lastName: String, address: String, oldPassword: String): Response<User> {
        return api.updateUserProfile(UserUpdateRequest(email, newPassword, firstName, lastName, address, oldPassword))
    }

    // Submit an order with the user's email and a list of products
    suspend fun submitOrderWithEmail(email: String, products: List<OrderProduct>): Response<Void> {
        val orderRequest = OrderWithEmailRequest(email, products)
        return api.submitOrderWithEmail(orderRequest)
    }
}
