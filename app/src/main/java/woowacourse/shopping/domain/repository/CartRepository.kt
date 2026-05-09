package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product

interface CartRepository {
    val cartItemCount: Int

    fun addItem(
        product: Product,
        amount: Int,
    )

    fun deleteItem(productId: String)

    suspend fun getCartItemByPage(page: Int): List<CartItem>

    fun isLastPage(page: Int): Boolean

    fun getItemCount(productId: String): Int

    fun plusItemCount(product: Product)

    fun minusItemCount(productId: String)
}
