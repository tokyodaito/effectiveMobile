package com.bogsnebes.effectivemobile.ui.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogsnebes.effectivemobile.model.impl.ProductRepository
import com.bogsnebes.effectivemobile.model.network.ProductResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) :
    ViewModel() {

    private val _products = MutableLiveData<ProductResponse>()
    val products: LiveData<ProductResponse> = _products

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            try {
                val productResponse = productRepository.getProducts()
                _products.value = productResponse
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }
}
