package com.example.domain

data class CartProduct(
    val productId: Int,
    val productImageUrl: String,
    val productName: String,
    val productPrice: Int,
    val count: Int,
) {

    fun updateCount(count: Int): CartProduct {
        return copy(count = count)
    }
}
