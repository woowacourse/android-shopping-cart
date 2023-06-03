package com.example.domain

class CartProduct(
    val productId: Int,
    val productImageUrl: String,
    val productName: String,
    val productPrice: Int,
    var count: Int,
    var checked: Boolean
) {
    companion object {
        const val MIN_COUNT_VALUE = 1
        const val MAX_COUNT_VALUE = 99
    }
}
