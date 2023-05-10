package woowacourse.shopping.data.respository.recentproduct

import woowacourse.shopping.presentation.model.RecentProductModel

interface RecentProductRepository {
    fun getRecentProducts(): List<RecentProductModel>
    fun deleteAllRecentProducts()
    fun addCart(productId: Long)
}
