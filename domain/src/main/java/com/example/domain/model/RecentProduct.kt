package com.example.domain.model

import java.time.LocalDateTime

data class RecentProduct(
    val product: Product,
    val dateTime: LocalDateTime
)
