package woowacourse.shopping.data.repository

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.data.source.local.recent.RecentProductDao
import woowacourse.shopping.data.source.local.recent.RecentProductEntity
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

class LocalRecentProductRepository(
    private val recentProductDao: RecentProductDao,
    private val productRepository: ProductRepository,
    private val maxKeep: Int = 10,
) : RecentProductRepository {
    override suspend fun upsertRecentProduct(productId: String) {
        recentProductDao.upsertAndTrim(
            RecentProductEntity(
                productId = productId,
                lastViewedAt = System.currentTimeMillis(),
            ),
            keep = maxKeep
        )
    }

    override suspend fun getRecentProducts(limit: Int): ImmutableList<Product> {
        val rows = recentProductDao.getRecent(limit)
        return rows
            .mapNotNull { row ->
                runCatching { productRepository.getProductById(row.productId) }
                    .getOrNull()
            }.toImmutableList()
    }
}
