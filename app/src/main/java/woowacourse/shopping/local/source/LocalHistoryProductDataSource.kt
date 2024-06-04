package woowacourse.shopping.local.source

import woowacourse.shopping.data.model.HistoryProduct
import woowacourse.shopping.data.source.ProductHistoryDataSource
import woowacourse.shopping.local.history.HistoryProductDao
import kotlin.concurrent.thread

class LocalHistoryProductDataSource(private val dao: HistoryProductDao) : ProductHistoryDataSource {
    override fun saveProductHistoryAsyncResult(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    ) {
        thread {
            callback(
                runCatching {
                    val id = dao.findById(productId)

                    if (id != null) {
                        dao.delete(id)
                    }

                    dao.insert(HistoryProduct(productId))
                },
            )
        }
    }

    override fun loadLatestProductAsyncResult(callback: (Result<Long>) -> Unit) {
        thread {
            callback(
                runCatching {
                    dao.findLatest()?.id ?: throw NoSuchElementException()
                },
            )
        }
    }

    override fun loadAllProductHistoryAsyncResult(callback: (Result<List<Long>>) -> Unit) {
        thread {
            callback(
                runCatching {
                    dao.findAll().map { it.id }
                },
            )
        }
    }
}
