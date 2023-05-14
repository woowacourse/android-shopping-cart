package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts

interface RecentProductDataSource {
    fun insertRecentProduct(recentProduct: RecentProduct)
    fun selectAll(): RecentProducts
}
