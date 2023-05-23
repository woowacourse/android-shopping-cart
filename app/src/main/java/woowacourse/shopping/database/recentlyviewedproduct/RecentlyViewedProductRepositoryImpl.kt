package woowacourse.shopping.database.recentlyviewedproduct

import woowacourse.shopping.datasource.recentlyviewedproduct.RecentlyViewedProductDataSource
import woowacourse.shopping.domain.RecentlyViewedProduct
import woowacourse.shopping.repository.RecentlyViewedProductRepository

class RecentlyViewedProductRepositoryImpl(
    private val recentlyViewedProductDataSource: RecentlyViewedProductDataSource
) : RecentlyViewedProductRepository {

    override fun save(recentlyViewedProduct: RecentlyViewedProduct) {
        recentlyViewedProductDataSource.save(recentlyViewedProduct)
    }

    override fun findFirst10OrderByViewedTimeDesc(): List<RecentlyViewedProduct> {
        return recentlyViewedProductDataSource.findFirst10OrderByViewedTimeDesc()
    }
}
