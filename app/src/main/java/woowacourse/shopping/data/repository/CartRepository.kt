package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.Product

interface CartRepository {
    fun fetchCartProducts(page: Int): List<CartProduct>

    fun fetchMaxPageCount(): Int

    fun removeCartProduct(id: Int)

    fun clearCart()

    fun fetchAllProduct(): List<CartProduct>

    fun upsertCartProduct(
        product: Product,
        count: Int,
    )
}
