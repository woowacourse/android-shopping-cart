package woowacourse.shopping.data.repository

import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product

interface CartRepository {
    suspend fun getAllCartItems(): Cart

    suspend fun setQuantity(item: Product, quantity: Int)

    suspend fun delete(item: Product)

    suspend fun getPagedItems(
        fromIndex: Int,
        count: Int,
    ): List<CartItem>

    suspend fun getSize(): Int

    suspend fun getQuantity(item: Product): Int?
}
