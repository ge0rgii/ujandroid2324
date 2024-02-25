package com.simpleshop.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.simpleshop.api.AccountRepository
import com.simpleshop.api.RetrofitInstance
import com.simpleshop.models.User
import com.simpleshop.utils.SharedPreferencesHelper
import kotlinx.coroutines.launch
import retrofit2.Response

class AccountViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AccountRepository(RetrofitInstance.shopApi)
    val userLiveData = MutableLiveData<Response<User>>()
    private val sharedPreferencesHelper = SharedPreferencesHelper(application)

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            val response = repository.registerUser(email, password)
            if (response.isSuccessful && response.body() != null) {
                // Save user with the password since API doesn't return it
                saveLoggedInUser(response.body()!!)
                saveUserPassword(password)
            }
            userLiveData.postValue(response)
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val response = repository.loginUser(email, password)
            if (response.isSuccessful && response.body() != null) {
                // Again, save user with the password
                saveLoggedInUser(response.body()!!)
                saveUserPassword(password)

            }
            userLiveData.postValue(response)
        }
    }

    fun updateUserProfile(email: String, newPassword: String, firstName: String, lastName: String, address: String) {
        viewModelScope.launch {
            var newPassword = newPassword
            val oldPassword = sharedPreferencesHelper.getPassword()?: ""
            newPassword = if (newPassword.isEmpty()) oldPassword else newPassword
            val response = repository.updateUserProfile(email, newPassword, firstName, lastName, address, oldPassword)
            if (response.isSuccessful && response.body() != null) {
                // Update user details along with the new password
                saveLoggedInUser(response.body()!!)
                saveUserPassword(newPassword)
            }
            userLiveData.postValue(response)
        }
    }


    fun saveLoggedInUser(user: User) {
        sharedPreferencesHelper.saveLoggedInUser(user)
    }

    fun saveUserPassword(password: String) {
        sharedPreferencesHelper.savePassword(password)
    }

    fun getLoggedInUser(): User? {
        return sharedPreferencesHelper.getLoggedInUser()
    }

    fun logoutUser() {
        sharedPreferencesHelper.clearLoggedInUser()
    }
}
