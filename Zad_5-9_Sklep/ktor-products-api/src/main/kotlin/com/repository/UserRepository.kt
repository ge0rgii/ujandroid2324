package com.repository

import com.models.User

interface UserRepository {
    fun createUser(user: User): User?
    fun findUserByEmail(email: String): User?
    fun updateUser(user: User): User?
}
