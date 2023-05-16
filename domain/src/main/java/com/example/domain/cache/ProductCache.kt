package com.example.domain.cache

import com.example.domain.model.Product

interface ProductCache {
    val productList: List<Product>
    fun addProducts(products: List<Product>)
    fun clear()
}
