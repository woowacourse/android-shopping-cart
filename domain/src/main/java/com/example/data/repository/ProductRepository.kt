package com.example.data.repository

import com.example.domain.Product

interface ProductRepository {
    fun requestAll(): List<Product>
}
