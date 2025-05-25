package woowacourse.shopping.repository

import woowacourse.shopping.model.products.CartState
import woowacourse.shopping.model.products.Product

interface CartRepository {
    fun addProduct(product: Product): CartState

    fun updateQuantity(
        productId: String,
        quantity: Int,
    ): CartState

    fun removeProduct(productId: String): CartState

    fun getCurrentState(): CartState

    fun clear(): CartState
}
