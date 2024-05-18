package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.data.repsoitory.DummyData.ORDERS
import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.domain.model.OrderList
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import kotlin.math.min

object DummyShoppingCartRepositoryImpl : ShoppingCartRepository {
    private val orders: MutableList<Order> = ORDERS

    override fun addOrder(product: Product) {
        val id = orders.size + 1
        val order = Order(id, product)
        orders.add(order)
    }

    override fun removeOrder(orderId: Int) {
        orders.removeIf { it.id == orderId }
    }

    override fun removeAllOrder() {
        orders.clear()
    }

    override fun getPagingOrder(
        page: Int,
        pageSize: Int,
    ): Result<OrderList> =
        runCatching {
            val fromIndex = page * pageSize
            val toIndex = min(fromIndex + pageSize, orders.size)

            OrderList(orders.subList(fromIndex, toIndex), orders.size)
        }
}
