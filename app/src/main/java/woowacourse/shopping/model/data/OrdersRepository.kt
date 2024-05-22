package woowacourse.shopping.model.data

import android.content.Context
import kotlin.concurrent.thread

class OrdersRepository(application: Context) {
    private val database = OrderDatabase.getDatabase(application)
    private val orderDao: OrderDao = database.orderDao()

    fun getAllData(): List<OrderEntity> {
        var orders: List<OrderEntity> = emptyList()
        thread {
            orders = orderDao.getAll()
        }.join()
        return orders
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
