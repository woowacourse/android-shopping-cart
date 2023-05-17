package com.example.domain.model

data class CartProduct(
    val cartId: Long,
    val product: Product,
    var count: Int,
    var checked: Boolean
)
