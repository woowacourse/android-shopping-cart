package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.domain.model.PagingOrder
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import kotlin.math.min

object DummyShoppingCart : ShoppingCartRepository {
    private const val PAGE_SIZE = 1
    private val STUB_IMAGE_URL_A =
        "https://i.namu.wiki/i/VnSgJ92KZ4dSRF2_x3LAYiE-zafxvNochXYrt6QD88DNtVziOxYUVKploFydbFNY7rcmOBUEra42XObzSuBwww.webp"
    private val STUB_PRODUCT_A = Product(1, "홍차", 10000, STUB_IMAGE_URL_A)
    private val orders: MutableList<Order> =
        mutableListOf(
            Order(
                id = 1,
                product = STUB_PRODUCT_A,
            ),
        )

    override fun addOrder(product: Product) {
        val id = orders.size + 1
        val order = Order(id, product)
        orders.add(order)
    }

    override fun removeOrder(orderId: Int) {
        orders.removeIf { it.id == orderId }
    }

    override fun getOrderList(
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
