package woowacourse.shopping.data.preview

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.RecentProductRepository

class FakeRecentProductRepository : RecentProductRepository {
    private val recentProducts = MutableStateFlow<List<Product>>(emptyList())

    override fun getRecentProducts(limit: Int): Flow<List<Product>> = recentProducts.map { products -> products.take(limit) }

    override suspend fun getMostRecentProduct(): Product? = recentProducts.value.firstOrNull()

    override suspend fun save(product: Product) {
        val updated = listOf(product) + recentProducts.value.filterNot { it.id == product.id }
        recentProducts.value = updated.take(RecentProductRepository.DEFAULT_LIMIT)
    }
}
