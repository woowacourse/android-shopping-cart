package com.example.data.repository

import com.example.domain.Product
import com.example.domain.RecentProducts

interface RecentProductRepository {
    fun getRecentProducts(): RecentProducts
    fun getLastProduct(): Product?
    fun addColumn(product: Product)
}
