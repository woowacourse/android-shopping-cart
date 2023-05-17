package com.domain.model

interface RecentRepository {
    fun insert(product: Product)
    fun getRecent(maxSize: Int): List<Product>
    fun delete(id: Int)
    fun findById(id: Int): Product?
}
