package com.shopping.repository

import com.shopping.domain.CartProduct
import com.shopping.domain.Product

interface CartProductRepository {
    fun getAll(): List<CartProduct>
    fun getAllProductsCount(): Int
    fun insert(cartProduct: CartProduct)
    fun remove(cartProduct: CartProduct)
    fun loadCartProducts(index: Pair<Int, Int>): List<CartProduct>
    fun update(cartProduct: CartProduct)
    fun add(cartProduct: CartProduct)
    fun findCountById(id: Int): Int
    fun updateCount(product: Product, count: Int)
}
