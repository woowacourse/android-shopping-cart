package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.domain.model.Product

interface ShoppingCartRepository {
    fun addOrder(product: Product)

    fun removeOrder(orderId: Int)

    fun getOrderList(): Result<List<Order>>
}
