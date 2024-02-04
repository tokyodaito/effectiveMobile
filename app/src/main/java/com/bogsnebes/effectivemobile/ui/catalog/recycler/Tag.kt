package com.bogsnebes.effectivemobile.ui.catalog.recycler

data class Tag(
    val name: String,
    val filter: String,
    var isSelected: Boolean = false
)
