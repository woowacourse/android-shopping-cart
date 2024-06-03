package woowacourse.shopping.data.datasource.order

import woowacourse.shopping.data.db.dao.OrderDao
import woowacourse.shopping.data.db.mapper.toEntity
import woowacourse.shopping.data.db.mapper.toOrder
import woowacourse.shopping.data.db.model.OrderEntity
import woowacourse.shopping.domain.datasource.OrderDataSource
import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.domain.model.Product

class LocalOrderDataSource(
    private val orderDao: OrderDao,
) : OrderDataSource {
    override fun putOrder(order: Order) = orderDao.putOrder(order.toEntity())

    override fun putOrder(product: Product) {
        orderDao.putOrder(OrderEntity(quantity = 1, product = product.toEntity()))
    }

    override fun putOrder(
        product: Product,
        count: Int,
    ) {
        orderDao.putOrder(OrderEntity(quantity = count, product = product.toEntity()))
    }

    override fun getOrders(): List<Order> = orderDao.getOrders().toOrder()

    override fun getOrderByProductId(productId: Int): List<Order> = orderDao.getOrderByProductId(productId).toOrder()

    override fun getOrderById(orderId: Int): Order = orderDao.getOrderById(orderId).toOrder()

    override fun removeOrder(order: Order) {
        orderDao.removeOrder(order.toEntity())
    }

    override fun removeOrder(orderId: Int) {
        orderDao.removeOrderById(orderId)
    }

    override fun removeAll() {
        orderDao.removeAll()
    }

    override fun findByOffsetAndSize(
        offset: Int,
        size: Int,
    ): List<Order> = orderDao.findByOffsetAndSize(offset, size).toOrder()

    override fun findByOffsetAndSizeReversed(
        offset: Int,
        size: Int,
    ): List<Order> = orderDao.findByOffsetAndSizeReversed(offset, size).toOrder().reversed()
}
