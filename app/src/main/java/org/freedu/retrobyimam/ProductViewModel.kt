package org.freedu.retrobyimam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.freedu.retrobyimam.ApiClient.apiService

class ProductViewModel:ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    init {
        viewModelScope.launch {
            fetchProducts()
        }
    }

    suspend fun fetchProducts() {
        try {
            val response = apiService.getProducts()
            _products.postValue(response)
        } catch (e: Exception) {
            // Handle error
        }
    }
}