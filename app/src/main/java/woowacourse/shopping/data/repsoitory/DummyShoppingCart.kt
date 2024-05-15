package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.domain.model.PagingOrder
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import kotlin.math.min

object DummyShoppingCart : ShoppingCartRepository {
    val order =
        Order(
            id = 1,
            product = DummyData.STUB_PRODUCT_A,
        )

    private val orders: MutableList<Order> =
        mutableListOf(
            order,
            order.copy(id = 2, product = DummyData.STUB_PRODUCT_B),
            order.copy(id = 3, product = DummyData.STUB_PRODUCT_C),
            order.copy(id = 4, product = DummyData.STUB_PRODUCT_A),
            order.copy(id = 5, product = DummyData.STUB_PRODUCT_B),
            order.copy(id = 6, product = DummyData.STUB_PRODUCT_C),
            order.copy(id = 7, product = DummyData.STUB_PRODUCT_A),
            order.copy(id = 8, product = DummyData.STUB_PRODUCT_B),
            order.copy(id = 9, product = DummyData.STUB_PRODUCT_C),
            order.copy(id = 10, product = DummyData.STUB_PRODUCT_A),
            order.copy(id = 11, product = DummyData.STUB_PRODUCT_B),
        )

    override fun addOrder(product: Product) {
        val id = orders.size + 1
        val order = Order(id, product)
        orders.add(order)
    }

    override fun removeOrder(orderId: Int) {
        orders.removeIf { it.id == orderId }
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
