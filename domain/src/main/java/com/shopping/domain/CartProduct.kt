package com.shopping.domain

data class CartProduct(
    val isPicked: Boolean = true,
    val count: Int = 1,
    val product: Product
)
