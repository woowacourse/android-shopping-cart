package woowacourse.shopping.data.recent

import woowacourse.shopping.data.recent.local.RecentProductLocalDataSource
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.repository.RecentProductRepository
import kotlin.concurrent.thread

class RecentProductRepositoryImpl(
    private val localDataSource: RecentProductLocalDataSource,
) : RecentProductRepository {
    override fun insert(
        productId: Long,
        onSuccess: () -> Unit,
    ) {
        thread {
            localDataSource.insert(RecentProductEntity(productId = productId))
            onSuccess()
        }
    }

    override fun getLastProduct(onSuccess: (RecentProduct?) -> Unit) {
        thread {
            onSuccess(localDataSource.getLastProduct()?.toDomain())
        }
    }

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
        onSuccess: (List<RecentProduct>) -> Unit,
    ) {
        thread {
            onSuccess(localDataSource.getPaged(limit, offset).map { it.toDomain() })
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
