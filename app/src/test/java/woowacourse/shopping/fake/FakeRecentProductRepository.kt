package woowacourse.shopping.fake

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.RecentProductRepository

class FakeRecentProductRepository(
    private val products: List<Product> = emptyList(),
) : RecentProductRepository {
    private val recentIds = mutableListOf<Long>()

    override suspend fun upsertRecentProduct(id: Long) {
        recentIds.remove(id)
        recentIds.add(0, id)
    }

    override suspend fun getRecentProducts(limit: Int): ImmutableList<Product> =
        recentIds
            .take(limit)
            .mapNotNull { id -> products.find { it.id == id } }
            .toImmutableList()
}
