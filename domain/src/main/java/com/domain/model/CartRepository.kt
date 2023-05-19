package com.domain.model

interface CartRepository {
    fun getAll(): List<Product>
    fun insert(product: Product, count: Int)
    fun getSubList(offset: Int, size: Int): List<Product>
    fun remove(id: Int)
}
