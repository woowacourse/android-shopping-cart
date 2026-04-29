package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product

interface CartRepository {
    fun getCartItems(): List<CartItem>

    fun addItem(
        product: Product,
        amount: Int,
    )

    fun deleteItem(id: String)
}
