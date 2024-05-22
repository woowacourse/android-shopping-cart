package woowacourse.shopping

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ShoppingCart
import woowacourse.shopping.domain.ShoppingCartItem

interface ShoppingRepository {
    fun products(): List<Product>

    fun products(
        startPosition: Int,
        offset: Int,
    ): List<Product>

    fun productById(id: Long): Product

    fun productsTotalSize(): Int
}
