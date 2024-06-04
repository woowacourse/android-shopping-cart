package study

import woowacourse.shopping.data.model.HistoryProduct
import woowacourse.shopping.local.history.HistoryProductDao
import kotlin.concurrent.thread

class LocalHistoryProductDataSourceStudy(private val dao: HistoryProductDao) : ProductHistoryDataSourceStudy {
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

    override fun saveProductHistoryAsync(
        productId: Long,
        callback: (Boolean) -> Unit,
    ) {
        thread {
            val id = dao.findById(productId)

            if (id != null) {
                dao.delete(id)
            }

            dao.insert(HistoryProduct(productId))
            callback(true)
        }
    }

    override fun loadProductHistoryAsync(
        productId: Long,
        callback: (Long) -> Unit,
    ) {
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

    override fun saveProductHistoryResult(productId: Long): Result<Unit> {
        return runCatching {
            val id = dao.findById(productId)

            if (id != null) {
                dao.delete(id)
            }

            dao.insert(HistoryProduct(productId))
        }
    }

    override fun loadProductHistoryResult(productId: Long): Result<Long> {
        return runCatching {
            dao.findById(productId)?.id ?: throw NoSuchElementException()
        }
    }

    override fun loadLatestProductResult(): Result<Long> {
        return runCatching {
            dao.findLatest()?.id ?: throw NoSuchElementException()
        }
    }

    override fun loadAllProductHistoryResult(): Result<List<Long>> {
        return runCatching {
            dao.findAll().map { it.id }
        }
    }

    override fun saveProductHistoryAsyncResult(productId: Long, callback: (Result<Unit>) -> Unit) {
        thread {
            callback(runCatching {
                val id = dao.findById(productId)

                if (id != null) {
                    dao.delete(id)
                }

                dao.insert2(HistoryProduct(productId))
            })
        }
    }

    override fun loadProductHistoryAsyncResult(productId: Long, callback: (Result<Long>) -> Unit) {
        thread {
            callback(runCatching {
                dao.findById(productId)?.id ?: throw NoSuchElementException()
            })
        }
    }

    override fun loadLatestProductAsyncResult(callback: (Result<Long>) -> Unit) {
        thread {
            callback(runCatching {
                dao.findLatest()?.id ?: throw NoSuchElementException()
            })
        }
    }

    override fun loadAllProductHistoryAsyncResult(callback: (Result<List<Long>>) -> Unit) {
        thread {
            callback(runCatching {
                dao.findAll().map { it.id }
            })
        }
    }

    companion object {
        private const val EMPTY = -1L
    }
}
