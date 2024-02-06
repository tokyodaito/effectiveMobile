package com.bogsnebes.effectivemobile.model.network

import io.reactivex.Single
import retrofit2.http.GET

interface ProductService {
    @GET("97e721a7-0a66-4cae-b445-83cc0bcf9010")
    fun getProducts(): Single<ProductResponse>
}

