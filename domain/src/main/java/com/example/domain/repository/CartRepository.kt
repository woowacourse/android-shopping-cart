package com.example.domain.repository

import com.example.domain.model.CartProduct
import com.example.domain.model.Product

interface CartRepository {
    fun getAll(): List<CartProduct>
    fun addProduct(product: Product, count: Int)
    fun deleteProduct(product: Product)
    fun updateSelection(product: Product, isSelected: Boolean)
    fun getProductsByPage(page: Int, size: Int): List<CartProduct>
}
