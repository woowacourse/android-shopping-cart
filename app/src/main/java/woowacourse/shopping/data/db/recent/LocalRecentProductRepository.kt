package woowacourse.shopping.data.db.recent

import woowacourse.shopping.data.db.recent.RecentProductEntity.Companion.toEntity
import woowacourse.shopping.domain.RecentProductItem
import woowacourse.shopping.domain.repository.RecentRepository
import kotlin.concurrent.thread

class LocalRecentProductRepository(private val dao: RecentProductDao) : RecentRepository {
    override fun loadAll(): Result<List<RecentProductItem>> {
        var result: Result<List<RecentProductItem>>? = null
        thread {
            result =
                runCatching {
                    dao.loadAll().map { entity ->
                        entity.toDomain()
                    }
                }
        }.join()
        return result ?: throw NoSuchElementException()
    }

    override fun loadMostRecent(): Result<RecentProductItem?> {
        var result: Result<RecentProductItem?>? = null
        thread {
            result =
                runCatching {
                    val entity = dao.getMostRecent()
                    entity?.toDomain()
                }
        }.join()
        return result ?: throw NoSuchElementException()
    }

    override fun add(recentProduct: RecentProductItem): Result<Long> {
        return runCatching {
            dao.insert(
                recentProduct.toEntity(),
            )
        }
    }
}
