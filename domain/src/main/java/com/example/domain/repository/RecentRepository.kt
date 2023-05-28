package com.example.domain.repository

import com.example.domain.model.Product

interface RecentRepository {
    fun insert(product: Product)
    fun getRecent(maxSize: Int): List<Product>
    fun delete(id: Long)
    fun findById(id: Long): Product?
}
