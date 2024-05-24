package woowacourse.shopping.data.local

import woowacourse.shopping.data.local.RecentProductEntity.Companion.toEntity
import woowacourse.shopping.domain.RecentProductItem
import woowacourse.shopping.domain.RecentRepository

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
