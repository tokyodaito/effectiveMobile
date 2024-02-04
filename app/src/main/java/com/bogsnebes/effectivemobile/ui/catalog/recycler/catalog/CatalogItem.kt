package com.bogsnebes.effectivemobile.ui.catalog.recycler.catalog

data class CatalogItem(
    val id: Int,
    val price: String,
    val discountPrice: String,
    val discountPercentage: String,
    val productName: String,
    val productDescription: String,
    val rating: String,
    val imageUrls: List<String>
)

