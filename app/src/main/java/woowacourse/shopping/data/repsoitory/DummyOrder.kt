package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.data.DummyData
import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.domain.model.PagingOrder
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.OrderRepository
import kotlin.math.max
import kotlin.math.min

object DummyOrder : OrderRepository {
    val order =
        Order(
            id = 1,
            quantity = 2,
            product = DummyData.STUB_PRODUCT_1,
        )

    private val orders: MutableList<Order> =
        mutableListOf(
            order,
            order.copy(id = 2, product = DummyData.STUB_PRODUCT_2),
            order.copy(id = 3, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 4, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 5, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 6, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 7, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 8, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 9, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 10, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 11, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 12, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 13, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 14, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 15, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 16, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 17, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 18, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 19, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 20, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 21, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 22, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 23, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 24, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 25, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 26, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 27, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 28, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 29, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 30, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 31, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 32, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 33, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 34, product = DummyData.STUB_PRODUCT_3),
            order.copy(id = 35, product = DummyData.STUB_PRODUCT_3),
        )

    override fun plusOrder(product: Product) {
        val order = orders.find { it.product == product }
        val id = order?.id ?: (orders.lastOrNull()?.id?.plus(1)) ?: 1
        val quantity = order?.quantity?.plus(1) ?: 1
        orders.removeIf { it.id == id }
        val newOrder = Order(id, quantity, product)
        orders.add(newOrder)
    }

    override fun plusOrder(
        product: Product,
        count: Int,
    ) {
        repeat(count) {
            plusOrder(product)
        }
    }

    override fun plusOrder(orderId: Int) {
        val order = orders.find { order -> order.id == orderId } ?: throw NoSuchElementException()
        val newOrder = order.copy(quantity = order.quantity + 1)
        orders.removeIf { it.id == orderId }
        orders.add(newOrder)
    }

    override fun plusOrder(
        orderId: Int,
        count: Int,
    ) {
        repeat(count) {
            plusOrder(orderId)
        }
    }

    override fun minusOrder(product: Product) {
        val order = orders.find { it.product == product } ?: throw NoSuchElementException()
        val id = order.id
        val quantity = order.quantity.minus(1)
        orders.removeIf { it.id == id }
        if (quantity > 0) {
            val newOrder = Order(id, quantity, product)
            orders.add(newOrder)
        }
    }

    override fun minusOrder(orderId: Int) {
        val order = orders.find { order -> order.id == orderId } ?: throw NoSuchElementException()
        val newOrder = order.copy(quantity = order.quantity - 1)
        orders.removeIf { it.id == orderId }
        if (newOrder.quantity > 0) {
            orders.add(newOrder)
        }
    }

    override fun removeOrder(orderId: Int) {
        orders.removeIf { it.id == orderId }
    }

    override fun removeAllOrder() {
        orders.clear()
    }

    override fun getOrders(): List<Order> {
        return orders
    }

    override fun getPagingOrder(
        lastSeenId: Int,
        pageSize: Int,
    ): Result<PagingOrder> =
        runCatching {
            val fromIndex = lastSeenId
            val toIndex = min(fromIndex + pageSize, orders.size)
            val first = fromIndex == 0
            val last = toIndex == orders.size
            PagingOrder(
                orderList = orders.subList(fromIndex, toIndex).toList(),
                first = first,
                last = last,
            )
        }

    override fun getPagingOrderReversed(
        lastSeenId: Int,
        pageSize: Int,
    ): Result<PagingOrder> =
        runCatching {
            val toIndex = lastSeenId
            val fromIndex = max(toIndex - pageSize, 0)
            val first = fromIndex == 0
            val last = toIndex == orders.size
            PagingOrder(
                orderList = orders.subList(fromIndex, toIndex).toList(),
                first = first,
                last = last,
            )
        }

    override fun getOrderByProductId(productId: Int): Order? = orders.firstOrNull { it.product.id == productId }
}
