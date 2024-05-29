package woowacourse.shopping.ui

import woowacourse.shopping.model.data.OrderDao
import woowacourse.shopping.model.data.OrderEntity

object FakeOrderDao : OrderDao {
    private var id: Long = 0
    private val orders = mutableMapOf<Long, OrderEntity>()

    override fun deleteById(id: Long) {
        orders.remove(id)
    }

    override fun getAll(): List<OrderEntity> {
        return orders.map { it.value }
    }

    override fun getById(id: Long): OrderEntity? {
        return orders[id]
    }

    override fun deleteAll() {
        orders.clear()
    }

    override fun insert(order: OrderEntity) {
        orders[id] = order
        id++
    }
}
