package woowacourse.shopping

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ShoppingCart

interface ShoppingRepository {
    fun products(): List<Product>

    fun productById(id: Long): Product

    fun userId(): Long

    fun shoppingCart(userId: Long): ShoppingCart

    fun updateShoppingCart(shoppingCart: ShoppingCart)
}
