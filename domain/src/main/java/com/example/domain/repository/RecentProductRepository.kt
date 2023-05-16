package com.example.domain.repository

import com.example.domain.RecentProduct

interface RecentProductRepository {
    fun getAll(): List<RecentProduct>
    fun addRecentProduct(recentProduct: RecentProduct)
}
