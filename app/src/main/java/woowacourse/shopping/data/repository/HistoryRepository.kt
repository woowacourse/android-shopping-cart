package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.HistoryDao
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.data.model.entity.HistoryProductEntity
import woowacourse.shopping.domain.model.HistoryProduct
import woowacourse.shopping.domain.repository.HistoryRepository

class HistoryRepository(
    private val dao: HistoryDao,
) : HistoryRepository {
    override fun fetchAllHistory(): List<HistoryProduct> = dao.getHistoryProducts().map { it.toDomain() }

    override fun saveHistory(productId: Int) {
        dao.insertHistoryWithLimit(
            history = HistoryProductEntity(productId),
            limit = MAX_HISTORY_COUNT,
        )
    }

    override fun fetchRecentHistory(): HistoryProduct? = dao.getRecentHistoryProduct()?.toDomain()

    companion object {
        private const val MAX_HISTORY_COUNT = 10
    }
}
