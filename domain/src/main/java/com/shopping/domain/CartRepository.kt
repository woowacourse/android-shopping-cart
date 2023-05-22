package com.shopping.domain

interface CartRepository {
    fun getAll(): List<CartProduct>

    fun getUnitData(unitSize: Int, pageNumber: Int): List<CartProduct>

    fun updateProductIsPicked(productId: Int, isPicked: Boolean)

    fun updateProductCount(productId: Int, count: Int)

    fun getProductCount(productId: Int): Int

    fun getSize(): Int

    fun insert(cartProduct: CartProduct)

    fun remove(productId: Int)

    fun clear()

    fun isEmpty(): Boolean

    fun close()
}
