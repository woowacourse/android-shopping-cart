package com.example.domain.model

class CartProducts(
    items: List<CartProduct>
) {
    private val items: MutableList<CartProduct> = items.toMutableList()

    fun remove(product: Product) {
        items.removeIf { it.product.id == product.id }
    }

    fun changeCount(product: Product, count: Int) {
        val index = items.indexOfFirst { it.product.id == product.id }
        if (index != -1) {
            items[index] = items[index].copy(count = count)
        }
    }

    fun changeSelection(cartProduct: CartProduct, isSelected: Boolean) {
        val index = items.indexOfFirst { it == cartProduct }
        if (index != -1) {
            items[index] = items[index].copy(isSelected = isSelected)
        }
    }

    fun selectedItems(): List<CartProduct> {
        return items.filter { it.isSelected }
    }
}
