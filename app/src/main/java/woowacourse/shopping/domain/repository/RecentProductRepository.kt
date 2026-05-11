package woowacourse.shopping.domain.repository

interface RecentProductRepository {
    fun getRecentProductIds(): List<String>

    fun getLastViewProductId(): String

    fun addRecentProductId(productId: String)
}
