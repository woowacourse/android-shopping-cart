package woowacourse.shopping.repository

interface RecentProductRepository {

    fun addRecentProductId(recentProductId: Int)

    fun deleteRecentProductId(recentProductId: Int)
    fun getRecentProductIdList(size: Int): List<Int>
}
