package woowacourse.shopping.model.data

import kotlin.concurrent.thread

class OrdersRepository(private val orderDao: OrderDao) {
    fun getAllData(): List<OrderEntity> {
        var orders: List<OrderEntity> = emptyList()
        thread {
            orders = orderDao.getAll()
        }.join()
        return orders
    }

    fun getById(id: Long): OrderEntity {
        var order = OrderEntity(productId = id, quantity = 0)
        thread {
            order = orderDao.getById(id) ?: OrderEntity(productId = id, quantity = 0)
        }.join()
        return order
    }

    fun insert(entity: OrderEntity) {
        thread {
            orderDao.insert(entity)
        }.join()
    }

    fun deleteAll() {
        thread {
            orderDao.deleteAll()
        }.join()
    }

    fun deleteById(id: Long) {
        thread {
            orderDao.deleteById(id)
        }.join()
    }
}
