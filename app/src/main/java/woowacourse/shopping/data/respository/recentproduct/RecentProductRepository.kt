package woowacourse.shopping.data.respository.recentproduct

import woowacourse.shopping.data.model.RecentProductEntity

interface RecentProductRepository {
    fun getRecentProducts(limit: Int): List<RecentProductEntity>
    fun deleteNotTodayRecentProducts(today: String)
    fun addCart(productId: Long)
}
