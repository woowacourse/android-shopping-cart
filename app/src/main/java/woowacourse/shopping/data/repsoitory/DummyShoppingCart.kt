package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingCartRepository

object DummyShoppingCart : ShoppingCartRepository {
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

    override fun addOrder(order: Order) {
        orders.add(order)
    }

    override fun removeOrder(orderId: Int) {
        orders.removeIf { it.id == orderId }
    }

    override fun getOrderList(): Result<List<Order>> =
        runCatching {
            orders.toList()
        }
}
