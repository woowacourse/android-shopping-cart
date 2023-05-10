package com.example.domain.repository

import com.example.domain.model.CartItem
import com.example.domain.model.Product

interface CartRepository {
    fun getAll(): List<CartItem>
    fun addProduct(product: Product)
    fun deleteProduct(cartItem: CartItem)
}
