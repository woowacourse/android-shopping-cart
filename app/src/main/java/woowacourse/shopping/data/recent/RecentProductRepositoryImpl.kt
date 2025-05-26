package woowacourse.shopping.data.recent

import woowacourse.shopping.data.product.toLocalDateTime
import woowacourse.shopping.data.recent.local.RecentProductLocalDataSource
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import java.util.concurrent.CountDownLatch
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
            val latch = CountDownLatch(entities.size)

            val recentProducts = mutableListOf<RecentProduct>()
            entities.forEach { entity ->
                productRepository.getProductById(entity.productId) { product ->
                    product?.let {
                        val recentProduct = RecentProduct(it, entity.viewedAt.toLocalDateTime())
                        recentProducts.add(recentProduct)
                    }
                    latch.countDown()
                }
            }
            latch.await()
            onSuccess(recentProducts)
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
