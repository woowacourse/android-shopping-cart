package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.domain.repository.ShoppingCartRepository

object DummyShoppingCart : ShoppingCartRepository {
    private val orders: MutableList<Order> = mutableListOf()

    override fun addOrder(order: Order) {
        orders.add(order)
    }

    override fun removeOrder(orderId: Int) {
        orders.removeIf { it.id == orderId }
    }
}
