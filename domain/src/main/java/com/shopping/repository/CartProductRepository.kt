package com.shopping.repository

import com.shopping.domain.CartProduct

interface CartProductRepository {
    fun getAll(): List<CartProduct>

    fun insert(cartProduct: CartProduct)

    fun remove(cartProduct: CartProduct)
}
