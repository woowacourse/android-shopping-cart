package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.data.datasource.order.LocalOrderDataSource
import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.domain.model.PagingOrder
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.OrderRepository
import woowacourse.shopping.presentation.common.runOnOtherThread
import woowacourse.shopping.presentation.common.runOnOtherThreadAndReturn

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
                if (order.quantity > 0) localOrderDataSource.putOrder(order.copy(quantity = order.quantity - 1))
            }
        }

    override fun minusOrder(orderId: Int) =
        runOnOtherThread {
            val order = localOrderDataSource.getOrderById(orderId)
            if (order.quantity > 0) localOrderDataSource.putOrder(order.copy(quantity = order.quantity - 1))
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
        page: Int,
        pageSize: Int,
    ): Result<PagingOrder> =
        runOnOtherThreadAndReturn {
            runCatching {
                val orders = localOrderDataSource.findByOffsetAndSize(page, pageSize)
                val nextOrders = localOrderDataSource.findByOffsetAndSize(page, pageSize)
                val lastPage = nextOrders.isEmpty()
                PagingOrder(page, orders, lastPage)
            }
        }

    override fun getOrderByProductId(productId: Int): Order? =
        runOnOtherThreadAndReturn {
            localOrderDataSource.getOrderByProductId(productId).getOrNull(0)
        }
}
