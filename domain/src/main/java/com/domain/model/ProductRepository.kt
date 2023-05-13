package com.domain.model

interface ProductRepository {
    fun getAll(): List<Product>
    fun getNext(count: Int): List<Product>
    fun getUntil(offset: Int): List<Product>
    fun findById(id: Int): Product
}
