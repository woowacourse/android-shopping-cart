package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.RecentlyProduct

interface CurrentProductRepository {
    fun saveRecentlyProduct(recentlyProduct: RecentlyProduct)

    fun getMostRecentlyProduct(): RecentlyProduct

    fun getPagingRecentlyProduct():List<RecentlyProduct>

    fun removeRecentlyProduct(id: Long)
}
