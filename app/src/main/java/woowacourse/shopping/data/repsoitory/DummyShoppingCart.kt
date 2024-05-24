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
        val id = order?.id ?: (orders[orders.size - 1].id + 1)
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

    override fun getOrderByProductId(productId: Int): Order? = orders.firstOrNull { it.product.id == productId }
}
