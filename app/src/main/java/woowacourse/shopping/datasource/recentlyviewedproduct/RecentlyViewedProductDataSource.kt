package woowacourse.shopping.datasource.recentlyviewedproduct

import woowacourse.shopping.domain.RecentlyViewedProduct

interface RecentlyViewedProductDataSource {

    fun save(recentlyViewedProduct: RecentlyViewedProduct)

    fun findFirst10OrderByViewedTimeDesc(): List<RecentlyViewedProduct>
}