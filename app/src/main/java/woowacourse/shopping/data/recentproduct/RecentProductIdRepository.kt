package woowacourse.shopping.data.recentproduct

interface RecentProductIdRepository {

    fun addRecentProductId(recentProductId: Long)
    fun deleteRecentProductId(recentProductId: Long)
    fun getRecentProductIds(size: Int): List<Long>
}
