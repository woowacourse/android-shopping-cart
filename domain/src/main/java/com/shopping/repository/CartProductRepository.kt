package com.shopping.repository

import com.shopping.domain.CartProduct
import com.shopping.domain.Count

interface CartProductRepository {
    fun getAll(): List<CartProduct>
    fun insert(cartProduct: CartProduct)
    fun remove(cartProduct: CartProduct)
    fun loadCartProducts(index: Pair<Int, Int>): List<CartProduct>
    fun update(cartProduct: CartProduct, count: Count)
}
