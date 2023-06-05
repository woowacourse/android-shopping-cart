package com.example.domain

class Cart(
    products: List<CartProduct> = listOf()
) {
    private var _products: MutableList<CartProduct> = products.toMutableList()

    val products: List<CartProduct>
        get() = _products.toList()

    fun isAllChecked(): Boolean = products.count() == products.count { it.checked }

    fun getByProductId(productId: Int): CartProduct? = _products.find { it.productId == productId }

    fun getCheckedItemCount(): Int = _products.count { it.checked }

    fun getCheckedProductsTotalPrice(): Int = products
        .filter { it.checked }
        .sumOf { it.count * it.productPrice }

    fun addProduct(product: Product) = _products.add(
        CartProduct(
            productId = product.id, productImageUrl = product.imageUrl, productName = product.name,
            productPrice = product.price, count = 1, checked = false
        )
    )

    fun updateAll(cartProducts: List<CartProduct>) {
        _products = cartProducts.toMutableList()
    }

    fun removeByProductId(productId: Int): Boolean =
        _products.removeIf { it.productId == productId }

    fun removeByIndex(index: Int): CartProduct = _products.removeAt(index)

    fun updateCheckedByProductId(productId: Int, checked: Boolean) {
        val index = getIndexByProductId(productId)
        _products[index].checked = checked
    }

    fun setAllChecked(checked: Boolean) = _products.map { it.checked = checked }

    fun subList(fromIndex: Int, toIndex: Int): List<CartProduct> {
        return if (toIndex < _products.size) _products.subList(fromIndex, toIndex)
        else _products.subList(fromIndex, _products.size)
    }

    private fun getIndexByProductId(productId: Int): Int =
        _products.indexOfFirst { it.productId == productId }
}
