package woowacourse.shopping.data.recent

import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.data.mapper.toEntity
import woowacourse.shopping.data.recent.local.RecentProductLocalDataSource
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.repository.RecentProductRepository
import kotlin.concurrent.thread

class RecentProductRepositoryImpl(
    private val localDataSource: RecentProductLocalDataSource,
) : RecentProductRepository {
    override fun insert(productId: Long) {
        thread { localDataSource.insert(RecentProductEntity(productId = productId)) }.join()
    }

    override fun getAll(): List<RecentProduct> {
        var result = listOf<RecentProduct>()
        thread { result = localDataSource.getAll().map { it.toDomain() } }.join()
        return result
    }

    override fun getLastProduct(): RecentProduct? {
        var result: RecentProduct? = null
        thread { result = localDataSource.getLastProduct()?.toDomain() }.join()
        return result
    }

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): List<RecentProduct> {
        var result = listOf<RecentProduct>()
        thread { result = localDataSource.getPaged(limit, offset).map { it.toDomain() } }.join()
        return result
    }

    override fun replaceRecentProduct(recentProduct: RecentProduct) {
        thread { localDataSource.replaceRecentProduct(recentProduct.toEntity()) }.join()
    }
}
