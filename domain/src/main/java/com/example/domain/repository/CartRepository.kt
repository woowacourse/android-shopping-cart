package com.example.domain.repository

import com.example.domain.model.CartProduct
import com.example.domain.model.Product

interface CartRepository {
    fun getAll(): List<CartProduct>
    fun addProduct(product: Product)
    fun deleteProduct(cartProduct: CartProduct)
    fun getPreviousProducts(size: Int, topId: Long): List<CartProduct>
    fun getNextProducts(size: Int, bottomId: Long): List<CartProduct>
    fun getProductFromId(size: Int, startId: Long): List<CartProduct>
}
