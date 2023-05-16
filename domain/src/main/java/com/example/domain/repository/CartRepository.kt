package com.example.domain.repository

import com.example.domain.model.CartProduct
import com.example.domain.model.Product

interface CartRepository {
    fun getAll(): List<CartProduct>
    fun getAllSize(): Int
    fun addProduct(product: Product)
    fun deleteProduct(cartProduct: CartProduct)
    fun changeCartProductCount(product: Product, count: Int)
    fun getInitPageProducts(pageSize: Int): List<CartProduct>
    fun getPreviousProducts(pageSize: Int, nextId: Long?): List<CartProduct>
    fun getNextProducts(pageSize: Int, previousId: Long?): List<CartProduct>
    fun getPageCartProductsFromFirstId(pageSize: Int, firstId: Long?): List<CartProduct>
    fun getCartProductsFromPage(pageSize: Int, page: Int): List<CartProduct>
}
