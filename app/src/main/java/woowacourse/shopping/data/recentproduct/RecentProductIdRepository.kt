package woowacourse.shopping.data.recentproduct

interface RecentProductIdRepository {

    fun addRecentProductId(recentProductId: Int)
    fun deleteRecentProductId(recentProductId: Int)
    fun getRecentProductIds(size: Int): List<Int>
}
