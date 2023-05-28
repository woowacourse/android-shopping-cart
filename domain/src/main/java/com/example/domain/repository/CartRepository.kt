package com.example.domain.repository

import com.example.domain.model.CartProduct
import com.example.domain.model.Product

interface CartRepository {
    fun getAll(): List<CartProduct>
    fun getAllCartProductCategorySize(): Int
    fun getAllCountSize(): Int
    fun deleteProduct(cartProduct: CartProduct)
    fun deleteAllCheckedCartProduct()
    fun changeCartProductCount(product: Product, count: Int)
    fun changeCartProductCheckedState(product: Product, checked: Boolean)
    fun changeCurrentPageAllCheckedState(cartIds: List<Long>, checked: Boolean)
    fun getCartProductsFromPage(pageSize: Int, page: Int): List<CartProduct>
}
