package woowacourse.shopping.data.recentproduct

interface RecentProductLocalDataSource {
    fun getRecentProductIdList(size: Int): List<Int>
    fun getMostRecentProductId(): Int
    fun addRecentProduct(recentProductId: Int)
    fun deleteRecentProduct(recentProductId: Int)
}
