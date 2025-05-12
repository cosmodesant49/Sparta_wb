package com.example.sparta_wb.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sparta_wb.data.remote.api.RetrofitClient
import com.example.sparta_wb.data.remote.model.product.Product
import com.example.sparta_wb.data.remote.model.product.ProductResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadProducts() {
        RetrofitClient.instance.getProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    _products.value = response.body()?.products
                } else {
                    _error.value = "Ошибка: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                _error.value = "Ошибка сети: ${t.message}"
            }
        })
    }

}
