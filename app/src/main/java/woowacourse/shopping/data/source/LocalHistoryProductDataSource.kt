package woowacourse.shopping.data.source

import woowacourse.shopping.data.model.HistoryProduct
import woowacourse.shopping.local.history.HistoryProductDao

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

    companion object {
        private const val EMPTY = -1L
    }
}
