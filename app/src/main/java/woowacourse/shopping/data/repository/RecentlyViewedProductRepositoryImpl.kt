package woowacourse.shopping.data.repository

import woowacourse.shopping.data.local.dao.RecentlyViewedProductDao
import woowacourse.shopping.data.local.entity.RecentlyViewedProductEntity
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.RecentlyViewedProducts
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentlyViewedProductRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class RecentlyViewedProductRepositoryImpl(
    private val dao: RecentlyViewedProductDao,
    private val productRepository: ProductRepository,
) : RecentlyViewedProductRepository {
    override suspend fun viewProduct(product: Product) {
        dao.save(
            RecentlyViewedProductEntity(
                productId = product.productId.toString(),
                viewedAt = System.currentTimeMillis(),
            ),
        )
        dao.deleteOverLimit()
    }

    override suspend fun getRecentlyViewedProducts(): RecentlyViewedProducts {
        val products =
            dao.findAll().mapNotNull { entity ->
                productRepository.findProductById(Uuid.parse(entity.productId))
            }

        return RecentlyViewedProducts(products)
    }
}
