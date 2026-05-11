package woowacourse.shopping.domain.repository

interface RecentProductRepository {
    suspend fun getRecentProductIds(): List<String>

    suspend fun getLastViewProductId(): String

    suspend fun addRecentProductId(productId: String)
}
