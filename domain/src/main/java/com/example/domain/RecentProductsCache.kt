package com.example.domain

class RecentProductsCache(
    private val _productIds: MutableList<Int> = mutableListOf(),
) {
    val productIds: List<Int>
        get() = _productIds

    fun addProduct(productId: Int) {
        if (_productIds.size > 10) {
            _productIds.removeLast()
        }
        _productIds.add(0, productId)
    }
}
