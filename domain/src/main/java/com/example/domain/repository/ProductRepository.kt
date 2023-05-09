package com.example.domain.repository

import com.example.domain.model.Product

interface ProductRepository {
    fun getAll(): List<Product>
    fun getNextProducts(lastProductId: Long): List<Product>
}
