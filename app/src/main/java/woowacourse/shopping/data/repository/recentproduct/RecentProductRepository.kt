package woowacourse.shopping.data.repository.recentproduct

interface RecentProductRepository {
    suspend fun loadProducts(): List<String>

    suspend fun insert(id: String)
}
