package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.local.RecentProductLocalDataSource
import woowacourse.shopping.data.util.toLocalDateTime
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.model.toEntity
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import kotlin.concurrent.thread

class RecentProductRepositoryImpl(
    private val localDataSource: RecentProductLocalDataSource,
    private val productRepository: ProductRepository,
) : RecentProductRepository {
    override fun getLastViewedProduct(onSuccess: (RecentProduct?) -> Unit) {
        thread {
            val entity = localDataSource.getLastViewedProduct()
            if (entity != null) {
                productRepository.getProductById(entity.productId) { product ->
                    val result =
                        product?.let { RecentProduct(it, entity.viewedAt.toLocalDateTime()) }
                    onSuccess(result)
                }
            } else {
                onSuccess(null)
            }
        }
    }

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
        onSuccess: (List<RecentProduct>) -> Unit,
    ) {
        thread {
            val entities = localDataSource.getPagedProducts(limit, offset)
            val productIds = entities.map { it.productId }
            productRepository.getProductsByIds(productIds) { products ->
                if (products == null) {
                    onSuccess(emptyList())
                    return@getProductsByIds
                }

                val productMap = products.associateBy { it.id }
                val recentProducts =
                    entities.mapNotNull { entity ->
                        productMap[entity.productId]?.let { product ->
                            RecentProduct(product, entity.viewedAt.toLocalDateTime())
                        }
                    }
                onSuccess(recentProducts)
            }
        }
    }

    override fun replaceRecentProduct(
        recentProduct: RecentProduct,
        onSuccess: () -> Unit,
    ) {
        thread {
            localDataSource.replaceRecentProduct(recentProduct.toEntity())
            onSuccess()
        }
    }
}
