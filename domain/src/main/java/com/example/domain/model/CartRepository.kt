package com.example.domain.model

interface CartRepository {
    fun getAll(): List<Product>
    fun add(item: Product)
    fun getSubList(offset: Int, size: Int): List<Product>
    fun remove(id: Int)
}
