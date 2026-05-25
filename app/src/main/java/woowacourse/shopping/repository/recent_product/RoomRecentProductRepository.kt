package woowacourse.shopping.repository.recent_product

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.dao.ShoppingDao
import woowacourse.shopping.data.entity.RecentProductEntity
import woowacourse.shopping.data.toDomain
import woowacourse.shopping.data.toEntity
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.RecentProductRepository

class RoomRecentProductRepository(
    private val dao: ShoppingDao,
) : RecentProductRepository {
    override val recentProducts: Flow<List<Product>> =
        dao.getRecentProductsWithDetail().map { entities ->
            entities.map { it.toDomain() }
                .toSet()
                .toList()
        }

    override suspend fun addRecentProduct(product: Product) {
        dao.insertProduct(listOf(product.toEntity()))
        dao.insertRecentProduct(RecentProductEntity(product.productId))
    }
}
