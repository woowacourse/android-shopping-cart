package com.example.domain.repository

import com.example.domain.model.Product

interface ProductRepository {
    fun getProducts(page: Int, onSuccess: (List<Product>) -> Unit)
    fun clearCache()
}
