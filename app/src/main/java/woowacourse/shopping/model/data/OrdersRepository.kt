package woowacourse.shopping.model.data

import android.content.Context
import androidx.lifecycle.LiveData

class OrdersRepository(application: Context) {
    private val database = OrderDatabase.getDatabase(application)
    private val orderDao: OrderDao = database.orderDao()

    fun getAllData(): LiveData<List<OrderEntity>> {
        return orderDao.getAll()
    }

    fun insert(entity: OrderEntity) {
        orderDao.insert(entity)
    }

    fun deleteAll() {
        orderDao.deleteAll()
    }

    fun deleteById(id: Long) {
        orderDao.deleteById(id)
    }
}
