package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product

interface CartRepository {
    suspend fun addItem(
        product: Product,
        amount: Int,
    )

    suspend fun deleteItem(id: String)

    suspend fun minusItemAmount(id: String)

    suspend fun getCartItemByPage(page: Int): List<CartItem>

    fun isLastPage(page: Int): Boolean
}
