package com.shopping.repository

import com.shopping.domain.Product

interface ProductRepository {
    val products: List<Product>
}
