package com.controllers

import com.models.User
import com.repository.UserRepository
import com.utils.HashingUtil

class UserController(private val userRepository: UserRepository) {

    fun createUser(user: User): User? {
        val userToCreate = user.copy(hashedPassword = user.hashedPassword)
        // Directly return the result from userRepository.createUser
        return userRepository.createUser(userToCreate)
    }

    fun validateUser(email: String, password: String): User? {
        val user = userRepository.findUserByEmail(email) ?: return null
        return if (HashingUtil.verifyPassword(password, user.hashedPassword)) user else null
    }

    fun updateUser(user: User): User? {
        return userRepository.updateUser(user)
    }
}
