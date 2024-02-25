package com.simpleshop.utils

import android.content.Context
import android.content.SharedPreferences
import com.simpleshop.models.User

class SharedPreferencesHelper(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    fun saveLoggedInUser(user: User) {
        with(prefs.edit()) {
            putString("email", user.email)
            putString("firstName", user.firstName)
            putString("lastName", user.lastName)
            putString("address", user.address)
            commit()
        }
    }

    // save password
    fun savePassword(password: String) {
        with(prefs.edit()) {
            putString("password", password)
            commit()
        }
    }

    fun getPassword(): String? {
        return prefs.getString("password", null)
    }

    fun getLoggedInUser(): User? {
        val email = prefs.getString("email", null) ?: return null
        val firstName = prefs.getString("firstName", null)
        val lastName = prefs.getString("lastName", null)
        val address = prefs.getString("address", null)
        val password = prefs.getString("password", null) ?: return null
        // Construct and return a User object
        return User(email = email, firstName = firstName, lastName = lastName, address = address, password = password)
    }

    fun clearLoggedInUser() {
        with(prefs.edit()) {
            clear()
            commit()
        }
    }
}
