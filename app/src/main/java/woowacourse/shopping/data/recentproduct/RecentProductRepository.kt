package woowacourse.shopping.data.recentproduct

interface RecentProductRepository {
    fun insert(productId: Long): Long

    fun findMostRecentProduct(): RecentProduct?

    fun findAll(): List<RecentProduct>

    fun deleteAll()
}
