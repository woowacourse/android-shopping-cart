package woowacourse.shopping.repository

import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product

interface CartRepository {
    suspend fun getAllCartItems(): Cart

    suspend fun add(
        item: Product,
        quantity: Int,
    )

    suspend fun increase(item: Product)

    suspend fun decrease(item: Product)

    suspend fun delete(item: Product)

    suspend fun getPagedItems(
        fromIndex: Int,
        count: Int,
    ): List<CartItem>

    suspend fun getSize(): Int
}
