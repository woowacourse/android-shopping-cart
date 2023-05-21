package com.example.domain.repository

import com.example.domain.model.CartProduct

interface CartRepository {
    fun getAll(): List<CartProduct>
    fun insert(product: CartProduct)
    fun getSubList(offset: Int, size: Int): List<CartProduct>
    fun remove(id: Long)

    fun updateCount(id: Long, count: Int)

    fun updateCheckChanged(id: Long, check: Boolean)

    fun getFindById(id: Long): CartProduct?
    fun getCheckCart(): List<CartProduct>
    fun getCartItemsPrice(): Int
}
