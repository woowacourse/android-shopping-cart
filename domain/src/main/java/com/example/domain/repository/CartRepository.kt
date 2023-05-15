package com.example.domain.repository

import com.example.domain.model.Product

interface CartRepository {
    fun getAll(): List<Product>
    fun insert(product: Product)
    fun getSubList(offset: Int, size: Int): List<Product>
    fun remove(id: Int)
}
