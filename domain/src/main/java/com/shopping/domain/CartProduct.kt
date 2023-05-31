package com.shopping.domain

data class CartProduct(
    val product: Product,
    val count: Count,
    val isSelected: Boolean
)
