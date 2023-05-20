package com.domain.model

interface CartRepository {
    fun getAll(): List<CartProduct>
    fun insert(product: Product, count: Int)
    fun getSubList(offset: Int, size: Int): List<CartProduct>
    fun remove(id: Int)
}
