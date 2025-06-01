package woowacourse.shopping.repository

import woowacourse.shopping.model.products.CartState

interface ShoppingCartRepository {
//    fun addProduct(product: Product): CartState
//
//    fun addProduct2(cart: ShoppingCartItem)

    fun addCart(
        productId: Int,
        quantity: Int,
    )

    fun getProductQuantity(productId: Int): Int

    fun updateQuantity(
        productId: Int,
        quantity: Int,
    ): CartState

    fun removeProduct(productId: Int): CartState

    fun getCurrentState(): CartState

    fun clear(): CartState
}
