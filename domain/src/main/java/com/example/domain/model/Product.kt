package com.example.domain.model

data class Product(
    val id: Long,
    val name: String,
    val imgUrl: String,
    val price: Price
)
