package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.domain.model.PagingOrder
import woowacourse.shopping.domain.model.Product

interface ShoppingCartRepository {
    fun plusOrder(product: Product)

    fun minusOrder(product: Product)

    fun removeOrder(orderId: Int)

    fun removeAllOrder()

    fun getOrders(): List<Order>

    fun getPagingOrder(
        page: Int,
        pageSize: Int,
    ): Result<PagingOrder>
}
