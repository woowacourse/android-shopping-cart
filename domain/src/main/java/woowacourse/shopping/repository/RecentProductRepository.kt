package woowacourse.shopping.repository

interface RecentProductRepository {

    fun addRecentProductId(recentProductId: Int)

    fun deleteRecentProductId(recentProductId: Int)
    fun getRecentProductIds(size: Int): List<Int>
}
