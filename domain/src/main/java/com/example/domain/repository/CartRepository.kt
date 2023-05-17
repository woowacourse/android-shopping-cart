package com.example.domain.repository

import com.example.domain.CartProduct
import com.example.domain.Product

interface CartRepository {
    fun getAll(): List<CartProduct>
    fun addProduct(product: Product)
    fun deleteCartProduct(cartProduct: CartProduct)
    fun updateCartProductCount(product: Product, count: Int)
    fun updateCartProductChecked(product: Product, checked: Boolean)

//    fun get(fromIndex: Int, ToIndex: Int): List<CartProduct>
//    fun getAllSize(): Int
}
