package com.simpleshop.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.simpleshop.api.RetrofitInstance
import com.simpleshop.models.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private var categories: MutableLiveData<List<Category>> = MutableLiveData()

    init {
        getCategories()
    }

    private fun getCategories() {
        RetrofitInstance.shopApi.getCategories().enqueue(object : Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                categories.value = response.body()
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Log.d("HomeViewModel", t.message.toString())
            }
        })
    }

    fun observeCategories(): LiveData<List<Category>> = categories
}
