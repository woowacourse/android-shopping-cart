package com.shopping.domain

class Cart(products: List<CartProduct>) {
    val products: List<CartProduct>

    init {
        this.products = products.distinctBy { it.product.id }
    }

    fun getTotalPrice(): Int = products.sumOf { it.count * it.product.price.value }
}
