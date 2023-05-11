package com.example.domain

class RecentProducts(
    private val _products: MutableList<Product> = mutableListOf(),
) {
    val products: List<Product>
        get() = _products

    fun addProduct(product: Product) {
        if (_products.size > 10) {
            _products.removeLast()
        }
        _products.add(0, product)
    }
}
