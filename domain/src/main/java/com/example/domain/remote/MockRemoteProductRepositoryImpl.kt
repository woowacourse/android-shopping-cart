package com.example.domain.remote

import com.example.domain.cache.ProductCache
import com.example.domain.cache.ProductLocalCache
import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository

class MockRemoteProductRepositoryImpl(
    private val service: MockProductRemoteService,
    override val cache: ProductCache = ProductLocalCache
) : ProductRepository {
    override fun getFirstProducts(): List<Product> {
        if (cache.productList.isEmpty()) {
            val products = service.request(0)
            cache.addProducts(products)
            return products
        }
        return cache.productList
    }

    override fun getNextProducts(lastProductId: Long): List<Product> {
        return service.request(lastProductId)
    }

    override fun resetCache() {
        cache.clear()
    }
}