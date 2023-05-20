package com.example.domain.cache

import com.example.domain.model.Product

object ProductLocalCache : ProductCache {
    private val _productList = mutableListOf<Product>()
    override val productList: List<Product>
        get() = _productList.toList()

    override fun addProducts(products: List<Product>) {
        _productList.addAll(products)
    }

    override fun clear() {
        _productList.clear()
    }
}
