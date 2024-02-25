package com.simpleshop.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.simpleshop.api.RetrofitInstance
import com.simpleshop.models.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsViewModel: ViewModel() {
    private var products = MutableLiveData<List<Product>>()

    fun getProductsByCategory(categoryId: Int) {
        RetrofitInstance.shopApi.getProductsByCategoryId(categoryId).enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    products.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.d("ProductsViewModel", t.message.toString())
            }
        })
    }

    fun observeProducts(): LiveData<List<Product>> = products
}
