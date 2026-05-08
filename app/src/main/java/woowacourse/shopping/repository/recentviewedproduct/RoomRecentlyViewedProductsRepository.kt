package woowacourse.shopping.repository.recentviewedproduct

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.local.dao.ProductDao
import woowacourse.shopping.data.local.dao.RecentlyViewedProductsDao
import woowacourse.shopping.data.local.entity.RecentViewedProductsEntity
import woowacourse.shopping.data.local.mapper.toDomain
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class RoomRecentlyViewedProductsRepository(
    private val recentlyViewedProductsDao: RecentlyViewedProductsDao,
    private val productsDao: ProductDao
) : RecentlyViewedProductsRepository {
    override suspend fun getLastViewedProduct(): Product? {
        val lastViewedProduct = recentlyViewedProductsDao.getLastViewedProduct() ?: return null
        return productsDao.getProductById(lastViewedProduct.productId)?.toDomain()
    }

    override fun getRecentlyViewedProducts(): Flow<Products> =
        recentlyViewedProductsDao.getRecentViewedProducts().map { recentViewedProducts ->
            Products(
                recentViewedProducts.mapNotNull { recentViewedProduct ->
                    productsDao.getProductById(recentViewedProduct.productId)?.toDomain()
                },
            )
        }

    override suspend fun saveViewedProduct(productId: Uuid) {
        recentlyViewedProductsDao.upsert(
            RecentViewedProductsEntity(
                productId = productId.toString(),
                viewedAt = System.currentTimeMillis(),
            ),
        )
        recentlyViewedProductsDao.deleteExceptLatest10()
    }
}