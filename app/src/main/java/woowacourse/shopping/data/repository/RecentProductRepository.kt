package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.RecentProductDataSource
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts

class RecentProductRepository(private val localDataSource: RecentProductDataSource) {
    fun insertRecentProduct(recentProduct: RecentProduct) {
        localDataSource.insertRecentProduct(recentProduct)
    }

    fun selectAll(): RecentProducts {
        return localDataSource.selectAll()
    }
}
