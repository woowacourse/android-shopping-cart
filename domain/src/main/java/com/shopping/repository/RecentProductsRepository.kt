package com.shopping.repository

import com.shopping.domain.RecentProduct

interface RecentProductsRepository {
    fun getAll(): List<RecentProduct>

    fun insert(recentProduct: RecentProduct)

    fun remove(recentProduct: RecentProduct)
    fun getFirst(): RecentProduct?
    fun isEmpty(): Boolean
}
