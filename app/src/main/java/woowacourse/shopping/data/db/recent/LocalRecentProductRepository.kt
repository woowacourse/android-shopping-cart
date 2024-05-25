package woowacourse.shopping.data.db.recent

import woowacourse.shopping.data.db.recent.RecentProductEntity.Companion.toEntity
import woowacourse.shopping.domain.RecentProductItem
import woowacourse.shopping.domain.repository.RecentRepository

class LocalRecentProductRepository(private val dao: RecentProductDao) : RecentRepository {
    override fun loadAll(): Result<List<RecentProductItem>> {
        return runCatching {
            dao.loadAll().map { entity ->
                entity.toDomain()
            }
        }
    }

    override fun loadMostRecent(): Result<RecentProductItem?> {
        return runCatching {
            val entity = dao.getMostRecent() ?: throw IllegalArgumentException()
            entity.toDomain()
        }
    }

    override fun add(recentProduct: RecentProductItem): Result<Long> {
        return runCatching {
            dao.insert(
                recentProduct.toEntity(),
            )
        }
    }
}
