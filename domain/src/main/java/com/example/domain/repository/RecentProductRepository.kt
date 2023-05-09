package com.example.domain.repository

import com.example.domain.model.Product

interface RecentProductRepository {
    fun getAll(): List<Product>
    fun addProduct(product: Product)
}
