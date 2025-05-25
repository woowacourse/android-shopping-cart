package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.RecentProductLocalDataSource
import woowacourse.shopping.data.entity.RecentProductEntity
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import kotlin.concurrent.thread

class RecentProductRepositoryImpl(
    private val recentProductLocalDataSource: RecentProductLocalDataSource,
    private val productRepository: ProductRepository,
) :
    RecentProductRepository {
    override fun insert(
        recentProduct: RecentProduct,
        onResult: (Long) -> Unit,
    ) {
        thread {
            val id = recentProductLocalDataSource.insert(recentProduct.toEntity())
            onResult(id)
        }
    }

    override fun getById(
        id: Long,
        onResult: (RecentProduct?) -> Unit,
    ) {
        thread {
            val product = productRepository.getById(id)
            onResult(recentProductLocalDataSource.getById(id)?.toModel(product))
        }
    }

    override fun getLatest(onResult: (RecentProduct?) -> Unit) {
        thread {
            val latestRecentProduct = recentProductLocalDataSource.getLatest()
            val product = productRepository.getById(latestRecentProduct?.productId ?: return@thread)
            onResult(latestRecentProduct.toModel(product))
        }
    }

    override fun getAll(onResult: (List<RecentProduct>) -> Unit) {
        thread {
            onResult(
                recentProductLocalDataSource.getAll().map {
                    val product = productRepository.getById(it.productId)
                    RecentProduct(product, it.createdDateTime)
                },
            )
        }
    }

    override fun deleteLastByCreatedDateTime(onResult: (Unit) -> Unit) {
        thread {
            onResult(recentProductLocalDataSource.deleteLastByCreatedDateTime())
        }
    }

    private fun RecentProductEntity.toModel(product: Product) = RecentProduct(product, createdDateTime)

    private fun RecentProduct.toEntity() = RecentProductEntity(product.id, createdDateTime)
}
