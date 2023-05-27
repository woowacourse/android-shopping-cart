package com.example.domain.repository

import com.example.domain.model.CartProduct

interface CartRepository {
    fun getAll(): List<CartProduct>
    fun insert(product: CartProduct)
    fun remove(id: Long)
    fun updateCount(id: Long, count: Int)
    fun findById(id: Long): CartProduct?
    fun getSubList(offset: Int, step: Int): List<CartProduct>
}
