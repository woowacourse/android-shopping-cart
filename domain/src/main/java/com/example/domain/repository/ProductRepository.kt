package com.example.domain.repository

import com.example.domain.Product

interface ProductRepository {

    fun fetchNextProducts(
        lastProductId: Long,
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    )

    fun getProduct(productId: Int): Product? = Product(0, "", "test product", 1_000)
}
