package woowacourse.shopping.data.respository.recentproduct

import woowacourse.shopping.presentation.model.RecentProductModel

interface RecentProductRepository {
    fun getRecentProducts(): List<RecentProductModel>
    fun deleteNotTodayRecentProducts(today: String)
    fun addCart(productId: Long)
}
