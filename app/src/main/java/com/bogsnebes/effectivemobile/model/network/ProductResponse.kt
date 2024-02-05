package com.bogsnebes.effectivemobile.model.network

data class ProductResponse(val items: List<ProductItem>)

data class ProductItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val price: ProductPrice,
    val feedback: ProductFeedback,
    val tags: List<String>,
    val available: Int,
    val description: String,
    val info: List<ProductInfo>,
    val ingredients: String
)

data class ProductPrice(
    val price: String,
    val discount: Int,
    val priceWithDiscount: String,
    val unit: String
)

data class ProductFeedback(
    val count: Int,
    val rating: Double
)

data class ProductInfo(
    val title: String,
    val value: String
)
