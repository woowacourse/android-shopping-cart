package com.example.domain.repository

import com.example.domain.model.Product

interface ProductRepository {
    fun getFirstProducts(): List<Product>
    fun getNextProducts(): List<Product>
    fun clearCache()
}
