package com.example.domain

class RecentProducts(
    private val _products: MutableList<Product> = mutableListOf(),
) {
    val products: List<Product>
        get() {
            val lastIndex = if (_products.size > MAX_INDEX) MAX_INDEX else _products.size
            return _products.subList(MIN_INDEX, lastIndex).toList()
        }

    fun addProduct(product: Product) {
        if (_products.size > 10) {
            _products.removeLast()
        }
        _products.add(0, product)
    }

    companion object {
        private const val MIN_INDEX = 0
        private const val MAX_INDEX = 10
    }
}
