package com.shopping.repository

import com.shopping.domain.Product

interface ProductRepository {
    fun loadProducts(index: Pair<Int, Int>): List<Product>
}
