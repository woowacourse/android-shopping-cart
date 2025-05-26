package woowacourse.shopping.data.product.repository

import woowacourse.shopping.data.product.local.dao.RecentWatchingDao
import woowacourse.shopping.data.product.local.entity.ProductEntity
import woowacourse.shopping.data.product.local.entity.RecentWatchingEntity
import woowacourse.shopping.data.product.storage.ProductRemoteStorage
import woowacourse.shopping.data.product.storage.ProductsStorage
import woowacourse.shopping.domain.product.Product
import kotlin.concurrent.thread

class DefaultProductsRepository(
    private val productsStorage: ProductsStorage = ProductRemoteStorage(),
    private val recentWatchingDao: RecentWatchingDao,
) : ProductsRepository {
    override fun load(
        lastProductId: Long?,
        size: Int,
        onResult: (Result<List<Product>>) -> Unit,
    ) {
        thread {
            runCatching {
                productsStorage.load(lastProductId, size).map(ProductEntity::toDomain)
            }.onSuccess { products ->
                onResult(Result.success(products))
            }.onFailure { exception ->
                onResult(Result.failure(exception))
            }
        }
    }

    override fun getRecentWatchingProducts(
        size: Int,
        onResult: (Result<List<Product>>) -> Unit,
    ) {
        thread {
            runCatching {
                recentWatchingDao.getRecentWatchingProducts(size)
            }.onSuccess { recentWatchingProducts: List<RecentWatchingEntity> ->
                onResult(Result.success(recentWatchingProducts.map { it.product }))
            }.onFailure { exception ->
                onResult(Result.failure(exception))
            }
        }
    }

    override fun updateRecentWatchingProduct(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    ) {
        thread {
            runCatching {
                recentWatchingDao.insertRecentWatching(
                    RecentWatchingEntity(
                        productId = product.id,
                        product = product,
                    ),
                )
            }.onSuccess {
                onResult(Result.success(Unit))
            }.onFailure { exception ->
                onResult(Result.failure(exception))
            }
        }
    }

    companion object {
        private var INSTANCE: ProductsRepository? = null

        fun initialize(recentWatchingDao: RecentWatchingDao) {
            if (INSTANCE == null) {
                INSTANCE = DefaultProductsRepository(recentWatchingDao = recentWatchingDao)
            }
        }

        fun get(): ProductsRepository = INSTANCE ?: throw IllegalStateException("초기화 되지 않았습니다.")
    }
}
