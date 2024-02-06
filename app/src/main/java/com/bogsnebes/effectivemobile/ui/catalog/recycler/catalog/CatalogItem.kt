package com.bogsnebes.effectivemobile.ui.catalog.recycler.catalog

data class CatalogItem(
    val id: String,
    val price: String,
    val discountPrice: String,
    val discountPercentage: String,
    val productName: String,
    val productDescription: String,
    val rating: String,
    var favorite: Boolean,
    val imageUrls: List<Int>
)

