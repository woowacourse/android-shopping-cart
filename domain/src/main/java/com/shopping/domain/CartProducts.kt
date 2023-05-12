package com.shopping.domain

class CartProducts(val products: List<Product>) {

    fun add(product: Product): CartProducts {
        return CartProducts(products + product)
    }

    fun remove(product: Product): CartProducts {
        return CartProducts(products - product)
    }
}
