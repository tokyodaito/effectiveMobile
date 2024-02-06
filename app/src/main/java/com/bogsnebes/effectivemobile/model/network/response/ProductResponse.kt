package com.bogsnebes.effectivemobile.model.network.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ProductResponse(val items: List<ProductItem>)

@Parcelize
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
) : Parcelable

@Parcelize
data class ProductPrice(
    val price: String,
    val discount: Int,
    val priceWithDiscount: String,
    val unit: String
) : Parcelable

@Parcelize
data class ProductFeedback(
    val count: Int,
    val rating: Double
) : Parcelable

@Parcelize
data class ProductInfo(
    val title: String,
    val value: String
) : Parcelable
