package com.example.domain

import com.example.domain.model.Product

interface ProductCache {
    val productList: List<Product>
    fun addProducts(products: List<Product>)
    fun clear()
}
