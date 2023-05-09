package com.example.domain.model

interface CartRepository {
    fun getAll(): List<Product>
    fun add(item: Product)
    fun remove(item: Product)
}
