package com.shopping.repository

import com.shopping.domain.Product

interface ProductRepository {
    fun loadProducts(
        index: Pair<Int, Int>,
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    )

    fun getProductById(
        index: Int,
        onSuccess: (Product) -> Unit,
        onFailure: () -> Unit
    )
}
