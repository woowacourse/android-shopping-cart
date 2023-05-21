package com.example.domain.repository

import com.example.domain.ProductCache
import com.example.domain.model.Product

interface ProductRepository {
    val productCache: ProductCache
    fun getFirstProducts(onSuccess: (List<Product>) -> Unit)
    fun getNextProducts(onSuccess: (List<Product>) -> Unit)
    fun clearCache()
}
