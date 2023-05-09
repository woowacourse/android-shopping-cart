package com.example.domain

import com.example.domain.model.Product

object Cart {
    private val _productList = mutableListOf<Product>()
    val productList get() = _productList.toList()

    fun add(product: Product) {
        _productList.add(product)
    }

    fun delete(product: Product) {
        _productList.remove(product)
    }
}
