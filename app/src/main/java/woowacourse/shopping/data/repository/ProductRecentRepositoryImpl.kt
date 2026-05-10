package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.local.RecentProductDao
import woowacourse.shopping.data.local.RecentProductEntity
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.repository.ProductRecentRepository

class ProductRecentRepositoryImpl(
    private val recentProductDao: RecentProductDao,
    private val productDataSource: ProductDataSource,
) : ProductRecentRepository {
    override fun getRecentProducts(limit: Int): Flow<List<RecentProduct>> {
        return recentProductDao.getRecentProducts(limit).map { recentProducts ->
            recentProducts.mapNotNull { recent ->
                val product = productDataSource.products.find { it.id == recent.productId }
                    ?: return@mapNotNull null

                RecentProduct(
                    productId = recent.productId,
                    name = product.name,
                    imageUrl = product.imageUrl,
                    viewedAt = recent.viewedAt,
                )
            }
        }
    }

    override suspend fun insertRecentProduct(productId: String) {
        recentProductDao.upsertRecentProduct(
            RecentProductEntity(
                productId = productId,
                viewedAt = System.currentTimeMillis(),
            ),
        )
    }

    override suspend fun getLatestViewedProduct(): RecentProduct? {
        val latestProduct = recentProductDao.getLatestViewedProduct() ?: return null
        val product =
            productDataSource.products.find { it.id == latestProduct.productId } ?: return null

        return RecentProduct(
            productId = latestProduct.productId,
            name = product.name,
            imageUrl = product.imageUrl,
            viewedAt = latestProduct.viewedAt,
        )
    }
}
