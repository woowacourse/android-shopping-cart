package com.example.domain.repository

import com.example.domain.CartProduct

interface CartRepository {
    fun getAll(): List<CartProduct>
    fun getCartProduct(productId: Int): CartProduct?
    fun addProduct(productId: Int, count: Int)
    fun deleteCartProduct(productId: Int)
    fun updateCartProductCount(productId: Int, count: Int)
    fun updateCartProductChecked(productId: Int, checked: Boolean)

//    fun get(fromIndex: Int, ToIndex: Int): List<CartProduct>
//    fun getAllSize(): Int
}
