package com.example.domain.model

interface ProductRepository {
    fun getAll(): List<Product>
    fun findById(id: Int): Product
}
