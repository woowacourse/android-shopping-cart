package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.local.dao.RecentProductDao
import woowacourse.shopping.data.local.entity.RecentProductEntity
import woowacourse.shopping.domain.Money
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.RecentProductRepository

class RecentProductRepositoryImpl(private val recentProductDao: RecentProductDao) : RecentProductRepository {
    override fun getRecentProducts(): Flow<List<Product>> = recentProductDao.getRecentProducts().map { entity ->
        entity.map { it.toProduct() }
    }

    override suspend fun addRecentProduct(product: Product) {
        recentProductDao.insertOrUpdate(product.toEntity())
    }

    private fun Product.toEntity(): RecentProductEntity = RecentProductEntity(
        productId = id,
        name = name,
        price = price.amount,
        imageUrl = imageUrl,
        viewedAt = System.currentTimeMillis(),
    )

    private fun RecentProductEntity.toProduct(): Product = Product(
        name = name,
        price = Money(price),
        imageUrl = imageUrl,
        id = productId,
    )
}
