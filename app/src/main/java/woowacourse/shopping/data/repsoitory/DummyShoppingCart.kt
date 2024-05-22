package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.data.DummyData
import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.domain.model.PagingOrder
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import kotlin.math.min

object DummyShoppingCart : ShoppingCartRepository {
    val order =
        Order(
            id = 1,
            quantity = 2,
            product = DummyData.STUB_PRODUCT_A,
        )

    private val orders: MutableList<Order> =
        mutableListOf(
            order,
            order.copy(id = 2, product = DummyData.STUB_PRODUCT_B),
            order.copy(id = 3, product = DummyData.STUB_PRODUCT_C),
        )

    override fun plusOrder(product: Product) {
        val order = orders.find { it.product == product }
        val id = order?.id ?: (orders.size + 1)
        val quantity = order?.quantity?.plus(1) ?: 1
        val newOrder = Order(id, quantity, product)
        orders.add(newOrder)
    }

    override fun minusOrder(product: Product) {
        val order =
            orders.find {
                it.product.id == product.id
            } ?: throw NoSuchElementException()

        if (order.quantity - 1 <= 0) {
            removeOrder(order.id)
            return
        }

        orders[order.id] = order.copy(quantity = order.quantity - 1)
    }

    override fun removeOrder(orderId: Int) {
        orders.removeAt(orderId)
    }

    override fun removeAllOrder() {
        orders.clear()
    }

    override fun getOrders(): List<Order> {
        return orders
    }

    override fun getPagingOrder(
        page: Int,
        pageSize: Int,
    ): Result<PagingOrder> =
        runCatching {
            val fromIndex = page * pageSize
            val toIndex = min(fromIndex + pageSize, orders.size)
            val last = toIndex == orders.size
            PagingOrder(
                currentPage = page,
                orderList = orders.subList(fromIndex, toIndex),
                last = last,
            )
        }
}
