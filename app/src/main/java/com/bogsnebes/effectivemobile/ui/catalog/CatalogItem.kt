package com.bogsnebes.effectivemobile.ui.catalog

import android.os.Parcelable
import com.bogsnebes.effectivemobile.model.network.ProductItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatalogItem(
    val item: ProductItem,
    var favorite: Boolean = false,
    var images: List<Int>
) : Parcelable