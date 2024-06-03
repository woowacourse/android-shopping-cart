package woowacourse.shopping.domain.datasource

import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.domain.model.Product

interface OrderDataSource {
    fun putOrder(order: Order)

    fun putOrder(product: Product)

    fun putOrder(
        product: Product,
        count: Int,
    )

    fun getOrders(): List<Order>

    fun getOrderByProductId(productId: Int): List<Order>

    fun getOrderById(orderId: Int): Order

    fun removeOrder(order: Order)

    fun removeOrder(orderId: Int)

    fun removeAll()

    fun findByOffsetAndSize(
        offset: Int,
        size: Int,
    ): List<Order>

    fun findByOffsetAndSizeReversed(
        offset: Int,
        size: Int,
    ): List<Order>
}
