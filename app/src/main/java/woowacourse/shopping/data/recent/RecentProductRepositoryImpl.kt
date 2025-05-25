package woowacourse.shopping.data.recent

import woowacourse.shopping.data.recent.local.RecentProductLocalDataSource
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.repository.RecentProductRepository
import kotlin.concurrent.thread

class RecentProductRepositoryImpl(
    private val localDataSource: RecentProductLocalDataSource,
) : RecentProductRepository {
    override fun getLastViewedProduct(onSuccess: (RecentProduct?) -> Unit) {
        thread {
            val result = localDataSource.getLastViewedProduct()?.toDomain()
            onSuccess(result)
        }
    }

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
        onSuccess: (List<RecentProduct>) -> Unit,
    ) {
        thread {
            val result = localDataSource.getPagedProducts(limit, offset).map { it.toDomain() }
            onSuccess(result)
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
