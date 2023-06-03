package com.example.domain

class CartProduct(
    val productId: Int,
    val productImageUrl: String,
    val productName: String,
    val productPrice: Int,
    count: Int,
    var checked: Boolean
) {

    var count = count
        private set

    fun plusCount(): Int {
        count = (++count).coerceAtMost(MAX_COUNT_VALUE)
        return count
    }

    fun minusCount(): Int {
        count = (--count).coerceAtLeast(MIN_COUNT_VALUE)
        return count
    }

    companion object {
        const val MIN_COUNT_VALUE = 1
        const val MAX_COUNT_VALUE = 99
    }
}
