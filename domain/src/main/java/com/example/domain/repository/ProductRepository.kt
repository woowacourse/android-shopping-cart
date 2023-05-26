package com.example.domain.repository

import com.example.domain.model.Product

interface ProductRepository {
    fun getFirstProducts(onSuccess: (List<Product>) -> Unit)
    fun getNextProducts(onSuccess: (List<Product>) -> Unit)
    fun clearCache()
}
