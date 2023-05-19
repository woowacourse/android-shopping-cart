package com.example.domain.repository

import com.example.domain.cache.ProductCache
import com.example.domain.model.Product

interface ProductRepository {
    val cache: ProductCache
    fun getFirstProducts(
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    )

    fun getNextProducts(
        lastProductId: Long,
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    )

    fun resetCache()

    companion object{
        val LOAD_SIZE = 20
    }
}
