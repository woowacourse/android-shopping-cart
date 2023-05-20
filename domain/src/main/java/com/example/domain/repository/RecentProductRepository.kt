package com.example.domain.repository

import com.example.domain.model.RecentProduct

interface RecentProductRepository {
    fun getAll(): List<RecentProduct>
    fun addRecentProduct(recentProduct: RecentProduct)
    fun getMostRecentProduct(): RecentProduct?
}
