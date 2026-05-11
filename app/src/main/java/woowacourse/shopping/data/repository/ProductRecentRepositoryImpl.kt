package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.local.RecentProductDao
import woowacourse.shopping.data.local.RecentProductEntity
import woowacourse.shopping.data.remote.ProductRemoteDataSource
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.repository.ProductRecentRepository

class ProductRecentRepositoryImpl(
    private val recentProductDao: RecentProductDao,
    private val dataSource: ProductRemoteDataSource,
) : ProductRecentRepository {
    override fun getRecentProducts(limit: Int): Flow<List<RecentProduct>> =
        recentProductDao.getRecentProducts(limit).map { recentProducts ->
            val ids = recentProducts.map { recent ->
                recent.productId
            }
            val products = dataSource.getProductsByIds(ids)
            recentProducts.map { recentProducts ->
                RecentProduct(
                    productId = recentProducts.productId,
                    name = products.find { it.id == recentProducts.productId }?.name ?: "",
                    imageUrl = products.find { it.id == recentProducts.productId }?.imageUrl ?: "",
                    viewedAt = recentProducts.viewedAt,
                )
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
        val product = dataSource.getProductById(latestProduct.productId)

        return RecentProduct(
            productId = latestProduct.productId,
            name = product.name,
            imageUrl = product.imageUrl,
            viewedAt = latestProduct.viewedAt,
        )
    }
}
