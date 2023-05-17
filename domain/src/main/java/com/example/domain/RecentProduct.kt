package com.example.domain

import java.time.LocalDateTime

data class RecentProduct(
    val productId: Int,
    val productImageUrl: String,
    val productName: String,
    val viewedDateTime: LocalDateTime
)
