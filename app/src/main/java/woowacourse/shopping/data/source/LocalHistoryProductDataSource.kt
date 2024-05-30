package woowacourse.shopping.data.source

import woowacourse.shopping.data.model.HistoryProduct
import woowacourse.shopping.local.history.HistoryProductDao
import kotlin.concurrent.thread

class LocalHistoryProductDataSource(private val dao: HistoryProductDao) : ProductHistoryDataSource {
    override fun saveProductHistory(productId: Long) {
        val id = dao.findById(productId)

        if (id != null) {
            dao.delete(id)
        }

        dao.insert(HistoryProduct(productId))
    }

    override fun loadProductHistory(productId: Long): Long? = dao.findById(productId)?.id

    override fun loadLatestProduct(): Long = dao.findLatest()?.id ?: EMPTY

    override fun loadAllProductHistory(): List<Long> = dao.findAll().map { it.id }

    override fun deleteAllProductHistory() {
        dao.deleteAll()
    }

    override fun saveProductHistoryAsync(productId: Long, callback: (Boolean) -> Unit) {
        thread {
            val id = dao.findById(productId)

            if (id != null) {
                dao.delete(id)
            }

            dao.insert(HistoryProduct(productId))
            callback(true)
        }
    }

    override fun loadProductHistoryAsync(productId: Long, callback: (Long) -> Unit) {
        thread {
            val id = dao.findById(productId)?.id
            callback(id ?: EMPTY)
        }
    }

    override fun loadLatestProductAsync(callback: (Long) -> Unit) {
        thread {
            val id = dao.findLatest()?.id ?: EMPTY
            callback(id)
        }
    }

    override fun loadAllProductHistoryAsync(callback: (List<Long>) -> Unit) {
        thread {
            val ids = dao.findAll().map { it.id }
            callback(ids)
        }
    }

    companion object {
        private const val EMPTY = -1L
    }
}
