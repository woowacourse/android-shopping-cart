package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.data.datasource.order.LocalOrderDataSource
import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.domain.model.PagingOrder
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.OrderRepository

class DefaultOrderRepository(
    private val localOrderDataSource: LocalOrderDataSource,
) : OrderRepository {
    override fun plusOrder(product: Product) =
        runOnOtherThread {
            val order = localOrderDataSource.getOrderByProductId(product.id).getOrNull(0)
            if (order == null) {
                localOrderDataSource.putOrder(product)
            } else {
                localOrderDataSource.putOrder(order.copy(quantity = order.quantity + 1))
            }
        }

    override fun plusOrder(
        product: Product,
        count: Int,
    ) = runOnOtherThread {
        val order = localOrderDataSource.getOrderByProductId(product.id).getOrNull(0)
        order?.let {
            localOrderDataSource.putOrder(order.copy(quantity = order.quantity + count))
        }
    }

    override fun plusOrder(orderId: Int) =
        runOnOtherThread {
            val order = localOrderDataSource.getOrderById(orderId)
            localOrderDataSource.putOrder(order.copy(quantity = order.quantity + 1))
        }

    override fun plusOrder(
        orderId: Int,
        count: Int,
    ) = runOnOtherThread {
        val order = localOrderDataSource.getOrderById(orderId)
        localOrderDataSource.putOrder(order.copy(quantity = order.quantity + count))
    }

    override fun minusOrder(product: Product) =
        runOnOtherThread {
            val order = localOrderDataSource.getOrderByProductId(product.id).getOrNull(0)
            order?.let {
                when {
                    order.quantity == 1 -> localOrderDataSource.removeOrder(order)
                    order.quantity > 0 -> localOrderDataSource.putOrder(order.copy(quantity = order.quantity - 1))
                }
            }
        }

    override fun minusOrder(orderId: Int) =
        runOnOtherThread {
            val order = localOrderDataSource.getOrderById(orderId)
            when {
                order.quantity == 1 -> localOrderDataSource.removeOrder(order)
                order.quantity > 0 -> localOrderDataSource.putOrder(order.copy(quantity = order.quantity - 1))
            }
        }

    override fun removeOrder(orderId: Int) =
        runOnOtherThread {
            localOrderDataSource.removeOrder(orderId)
        }

    override fun removeAllOrder() =
        runOnOtherThread {
            localOrderDataSource.removeAll()
        }

    override fun getOrders(): List<Order> =
        runOnOtherThreadAndReturn {
            localOrderDataSource.getOrders()
        }

    override fun getPagingOrder(
        lastSeenId: Int,
        pageSize: Int,
    ): Result<PagingOrder> =
        runOnOtherThreadAndReturn {
            runCatching {
                val orders = localOrderDataSource.findByOffsetAndSize(lastSeenId, pageSize)
                val nextOrders = localOrderDataSource.findByOffsetAndSize(lastSeenId, pageSize * 2)
                val prevOrders = localOrderDataSource.findByOffsetAndSizeReversed(lastSeenId, pageSize)
                val first = prevOrders.isEmpty()
                val last = nextOrders.size == orders.size
                PagingOrder(orders, first, last)
            }
        }

    override fun getPagingOrderReversed(
        lastSeenId: Int,
        pageSize: Int,
    ): Result<PagingOrder> =
        runOnOtherThreadAndReturn {
            runCatching {
                val orders = localOrderDataSource.findByOffsetAndSizeReversed(lastSeenId, pageSize)
                val prevOrders = localOrderDataSource.findByOffsetAndSizeReversed(lastSeenId, pageSize * 2)
                val nextOrders = localOrderDataSource.findByOffsetAndSize(lastSeenId, pageSize)
                val first = prevOrders.size == orders.size
                val last = nextOrders.isEmpty()
                PagingOrder(orders, first, last)
            }
        }

    override fun getOrderByProductId(productId: Int): Order? =
        runOnOtherThreadAndReturn {
            localOrderDataSource.getOrderByProductId(productId).getOrNull(0)
        }
}
