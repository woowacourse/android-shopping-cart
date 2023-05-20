package com.example.domain.repository

import com.example.domain.RecentProduct
import java.time.LocalDateTime

interface RecentProductRepository {
    fun getAll(): List<RecentProduct>
    fun addRecentProduct(productId: Int, viewedDateTime: LocalDateTime)
}
