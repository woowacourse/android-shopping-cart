package com.example.domain.repository

import com.example.domain.cache.ProductCache
import com.example.domain.model.Product

interface ProductRepository {
    val cache: ProductCache
    fun fetchFirstProducts(
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    )

    fun fetchNextProducts(
        lastProductId: Long,
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    )

    fun resetCache()
}
