package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product

interface CartRepository {
    fun addItem(
        product: Product,
        amount: Int,
    )

    fun deleteItem(productId: String)

    suspend fun getCartItemByPage(page: Int): List<CartItem>

    fun isLastPage(page: Int): Boolean
}
