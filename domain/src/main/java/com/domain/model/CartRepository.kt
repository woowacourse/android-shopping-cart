package com.domain.model

interface CartRepository {
    fun getAll(): List<CartProduct>
    fun insert(product: Product, count: Int)
    fun getSubList(offset: Int, size: Int): List<CartProduct>
    fun remove(id: Int)
    fun updateCount(id: Int, count: Int)
    fun updateChecked(id: Int, checked: Boolean)
    fun findById(id: Int): CartProduct?
    fun getChecked(): List<CartProduct>
}
