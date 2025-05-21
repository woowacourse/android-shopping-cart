package woowacourse.shopping.data.repository

import woowacourse.shopping.data.CartProductDTO
import woowacourse.shopping.domain.model.Product

interface CartRepository {
    fun fetchCartProducts(page: Int): List<CartProductDTO>

    fun fetchMaxPageCount(): Int

    fun removeCartProduct(id: Int)

    fun clearCart()

    fun fetchAllProduct(): List<CartProductDTO>

    fun upsertCartProduct(
        product: Product,
        count: Int,
    )
}
