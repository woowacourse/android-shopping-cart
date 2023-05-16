package woowacourse.shopping.data.recentproduct

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.repository.RecentProductRepository

class RecentProductRepositoryImpl(
    private val localRecentProductDataSource: RecentProductDataSource
) : RecentProductRepository {
    override fun addRecentProduct(recentProduct: RecentProduct) {
        localRecentProductDataSource.addRecentProduct(recentProduct)
    }

    override fun getAll(): RecentProducts {
        return localRecentProductDataSource.getAll()
    }

    override fun getByProduct(product: Product): RecentProduct? {
        return localRecentProductDataSource.getByProduct(product)
    }

    override fun modifyRecentProduct(recentProduct: RecentProduct) {
        localRecentProductDataSource.modifyRecentProduct(recentProduct)
    }
}
