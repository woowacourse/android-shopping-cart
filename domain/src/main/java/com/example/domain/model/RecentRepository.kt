package com.example.domain.model

interface RecentRepository {
    fun getRecent(maxSize: Int): List<Product>
    fun delete(id: Int): Boolean
    fun findById(id: Int): Product
}
