package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.RecentlyProduct

interface RecentlyProductRepository {
    fun addRecentlyProduct(recentlyProduct: RecentlyProduct)

    fun getMostRecentlyProduct(): RecentlyProduct

    fun getRecentlyProductList(): List<RecentlyProduct>

    fun deleteRecentlyProduct(id: Long)
}
