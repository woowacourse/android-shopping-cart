package com.example.domain.repository

import com.example.domain.cache.ProductCache
import com.example.domain.model.Product

interface ProductRepository {
    val cache: ProductCache
    fun getFirstProducts(): List<Product>
    fun getNextProducts(lastProductId: Long): List<Product>
    fun resetCache()
}
