package com.example.domain

import com.example.domain.model.Product

object ProductCache {
    private val _productList = mutableListOf<Product>()
    val productList: List<Product>
        get() = _productList.toList()

    fun addProducts(products: List<Product>) {
        _productList.addAll(products)
    }

    fun clear() {
        _productList.clear()
    }
}