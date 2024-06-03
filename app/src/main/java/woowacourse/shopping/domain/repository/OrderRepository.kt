package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.domain.model.PagingOrder
import woowacourse.shopping.domain.model.Product

interface OrderRepository {
    fun plusOrder(product: Product)

    fun plusOrder(
        product: Product,
        count: Int,
    )

    fun plusOrder(orderId: Int)

    fun minusOrder(product: Product)

    fun plusOrder(
        orderId: Int,
        count: Int,
    )

    fun minusOrder(orderId: Int)

    fun removeOrder(orderId: Int)

    fun removeAllOrder()

    fun getOrders(): List<Order>

    fun getPagingOrder(
        lastSeenId: Int,
        pageSize: Int,
    ): Result<PagingOrder>

    fun getPagingOrderReversed(
        lastSeenId: Int,
        pageSize: Int,
    ): Result<PagingOrder>

    fun getOrderByProductId(productId: Int): Order?
}
