package com.example.domain.repository

import com.example.domain.model.Product

interface CartRepository {
    fun getAll(): List<Product>
    fun addProduct(product: Product)
    fun deleteProduct(product: Product)
}
