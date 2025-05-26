package woowacourse.shopping.repository

import woowacourse.shopping.model.products.CartState
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.model.products.ShoppingCartItem

interface CartRepository {
    fun addProduct(product: Product): CartState

    fun addProduct2(cart: ShoppingCartItem)

    fun getProductQuantity(productId: String): Int

    fun updateQuantity(
        productId: String,
        quantity: Int,
    ): CartState

    fun removeProduct(productId: String): CartState

    fun getCurrentState(): CartState

    fun clear(): CartState
}
