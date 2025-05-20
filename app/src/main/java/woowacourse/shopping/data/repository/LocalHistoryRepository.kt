package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.HistoryDao
import woowacourse.shopping.data.entity.ExploreHistoryProductEntity
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.model.HistoryProduct
import woowacourse.shopping.domain.repository.HistoryRepository
import kotlin.concurrent.thread

class LocalHistoryRepository(
    private val historyDao: HistoryDao,
) : HistoryRepository {
    override fun fetchAllSearchHistory(): List<HistoryProduct> = historyDao.getHistoryProducts().map { it.toDomain() }

    override fun saveSearchHistory(productId: Int) {
        thread {
            historyDao.insertHistoryWithLimit(
                history = ExploreHistoryProductEntity(productId),
                limit = MAX_HISTORY_COUNT,
            )
        }
    }

    override fun fetchRecentSearchHistory(): HistoryProduct = historyDao.fetchRecentHistoryProduct().toDomain()

    companion object {
        private const val MAX_HISTORY_COUNT = 10
    }
}
