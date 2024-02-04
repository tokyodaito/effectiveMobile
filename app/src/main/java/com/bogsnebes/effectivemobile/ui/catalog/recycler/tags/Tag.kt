package com.bogsnebes.effectivemobile.ui.catalog.recycler.tags

data class Tag(
    val name: String,
    val filter: String,
    var isSelected: Boolean = false
)
