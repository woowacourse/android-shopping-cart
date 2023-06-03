package com.example.domain

class Cart(
    products: List<CartProduct>
) {
    private var _products: MutableList<CartProduct> = products.toMutableList()

    val products: List<CartProduct>
        get() = _products.toList()

    fun removeByProductId(productId: Int): Boolean = _products.removeIf { it.productId == productId }

    fun removeByIndex(index: Int): CartProduct = _products.removeAt(index)

    fun getCheckedItemCount(): Int = _products.count { it.checked }

    fun getPickedProductsTotalPrice(): Int = products
        .filter { it.checked }
        .sumOf { it.count * it.productPrice }

    fun updatePickedByIndex(productId: Int, picked: Boolean) {
        val index = getIndexByProductId(productId)
        _products[index].checked = picked
    }

    fun updateProductCountByIndex(productId: Int, count: Int) {
        val index = getIndexByProductId(productId)
        _products[index].count = count
    }

    fun isAllPicked(): Boolean = products.count() == products.count { it.checked }

    fun setAllPicked(picked: Boolean) = _products.map { it.checked = picked }

    private fun getIndexByProductId(productId: Int): Int = _products.indexOfFirst { it.productId == productId }
}
