package woowacourse.shopping.repository

import woowacourse.shopping.domain.RecentlyViewedProduct

interface RecentlyViewedProductRepository {

    fun save(recentlyViewedProduct: RecentlyViewedProduct)
    fun findFirst10OrderByViewedTimeDesc(): List<RecentlyViewedProduct>
}
