package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

interface ShoppingRepository {
    fun products(amount: Int): List<Product>

    fun productById(id: Long): Product

    fun cartProducts(): List<Product>

    fun deleteCartProduct(id: Long)
}
