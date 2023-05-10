package woowacourse.shopping.data.recentproduct

interface RecentProductIdRepository {

    fun addRecentProductId(recentProductId: Int)
    fun getRecentProductIds(size: Int): List<Int>
}
