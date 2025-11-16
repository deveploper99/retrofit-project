package com.example.project1.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project1.model.ProductModel
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val _products = MutableLiveData<List<ProductModel>>()
    val producs: LiveData<List<ProductModel>> get() = _products   // FIXED

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiServices.getProduct()  // suspend function
                _products.postValue(response.products)             // products list
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
