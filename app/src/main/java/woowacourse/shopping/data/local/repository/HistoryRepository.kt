package woowacourse.shopping.data.local.repository

import woowacourse.shopping.data.local.dao.HistoryDao
import woowacourse.shopping.data.local.entity.HistoryEntity
import kotlin.concurrent.thread

class HistoryRepository(private val dao: HistoryDao) {
    fun insert(id: Long, callback: (Long) -> Unit) {
        thread {
            dao.upsert(HistoryEntity(id))
            callback(id)
        }
    }

    fun getRecentProducts(limit: Int, callback: (List<Long>) -> Unit) {
        thread {
            val itemsId = dao.findRecentProduct(limit).map { it.id }
            callback(itemsId)
        }
    }
}
