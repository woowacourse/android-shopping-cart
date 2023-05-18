package com.example.domain

class CartProduct(
    val productId: Int,
    val productImageUrl: String,
    val productName: String,
    val productPrice: Int,
    val count: Int,
    val checked: Boolean
) {
    companion object {
        const val MIN_COUNT_VALUE = 1
        const val MAX_COUNT_VALUE = 99
    }
}
