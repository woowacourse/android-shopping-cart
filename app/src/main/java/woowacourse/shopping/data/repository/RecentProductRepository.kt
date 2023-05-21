package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.RecentProductDataSource
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts

class RecentProductRepository(private val dataSource: RecentProductDataSource) {
    fun insertRecentProduct(recentProduct: RecentProduct) {
        dataSource.insertRecentProduct(recentProduct)
    }

    fun selectAll(): RecentProducts {
        return dataSource.selectAll()
    }
}
