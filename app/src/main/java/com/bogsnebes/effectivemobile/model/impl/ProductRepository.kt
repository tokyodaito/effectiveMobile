package com.bogsnebes.effectivemobile.model.impl

import com.bogsnebes.effectivemobile.model.network.ProductResponse
import com.bogsnebes.effectivemobile.model.network.ProductService
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productService: ProductService) {
    suspend fun getProducts(): ProductResponse = productService.getProducts()
}
