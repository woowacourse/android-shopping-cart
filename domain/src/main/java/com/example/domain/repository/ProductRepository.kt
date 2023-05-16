package com.example.domain.repository

import com.example.domain.model.Product

interface ProductRepository {
    fun getAll(): List<Product>
    fun getNext(count: Int): List<Product>
    fun findById(id: Long): Product
}
