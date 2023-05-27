package com.example.domain.repository

import com.example.domain.model.Product

interface ProductRepository {
    fun getAll(onSuccess: (List<Product>) -> Unit, onFailure: (Exception) -> Unit)
    fun getNext(count: Int, onSuccess: (List<Product>) -> Unit, onFailure: (Exception) -> Unit)
    fun findById(id: Long, onSuccess: (Product) -> Unit, onFailure: (Exception) -> Unit)
}
