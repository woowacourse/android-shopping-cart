package woowacourse.shopping.data.product.repository

import woowacourse.shopping.data.product.local.dao.RecentWatchingDao
import woowacourse.shopping.data.product.local.entity.RecentWatchingEntity
import woowacourse.shopping.data.product.remote.dto.ProductResponseDto
import woowacourse.shopping.data.product.storage.ProductRemoteDataStorage
import woowacourse.shopping.data.product.storage.ProductRemoteStorage
import woowacourse.shopping.domain.product.Product
import kotlin.concurrent.thread

class DefaultProductsRepository(
    private val productRemoteDataStorage: ProductRemoteDataStorage = ProductRemoteStorage(),
    private val recentWatchingDao: RecentWatchingDao,
) : ProductsRepository {
    override fun load(
        lastProductId: Long?,
        size: Int,
        onResult: (Result<List<Product>>) -> Unit,
    ) {
        thread {
            runCatching {
                productRemoteDataStorage.load(lastProductId, size).map(ProductResponseDto::toDomain)
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
                val recentEntities = recentWatchingDao.getRecentWatchingProducts(size)
                val productIds = recentEntities.map { it.productId }

                getProductsByIds(productIds)
            }.onSuccess { products ->
                onResult(Result.success(products))
            }.onFailure { exception ->
                onResult(Result.failure(exception))
            }
        }
    }

    private fun getProductsByIds(productIds: List<Long>): List<Product> {
        val products =
            productIds.map { productId: Long ->
                productRemoteDataStorage.getProductsById(productId)
            }
        return products.map(ProductResponseDto::toDomain)
    }

    override fun updateRecentWatchingProduct(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        thread {
            runCatching {
                recentWatchingDao.insertRecentWatching(
                    RecentWatchingEntity(
                        productId = productId,
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
                INSTANCE =
                    DefaultProductsRepository(recentWatchingDao = recentWatchingDao)
            }
        }

        fun get(): ProductsRepository = INSTANCE ?: throw IllegalStateException("초기화 되지 않았습니다.")
    }
}
