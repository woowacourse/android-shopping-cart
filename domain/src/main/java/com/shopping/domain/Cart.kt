package com.shopping.domain

class Cart(val products: List<Product>) {

    fun add(product: Product): Cart {
        return Cart(products + product)
    }

    fun remove(product: Product): Cart {
        return Cart(products - product)
    }
}
