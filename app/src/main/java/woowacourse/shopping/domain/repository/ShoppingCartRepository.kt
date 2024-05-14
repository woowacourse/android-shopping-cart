package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Order

interface ShoppingCartRepository {
    fun addOrder(order: Order)

    fun removeOrder(orderId: Int)

    fun getOrderList(): Result<List<Order>>
}
