package woowacourse.shopping.helper

import woowacourse.shopping.data.db.recent.RecentProductRepository
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentProduct
import java.time.LocalDateTime
import kotlin.math.min

class FakeRecentProductRepositoryImpl : RecentProductRepository {
    private val recentProducts = mutableListOf<RecentProduct>()

    override fun save(product: Product) {
        recentProducts.add(
            RecentProduct(
                productId = product.id,
                productName = product.name,
                imageUrl = product.imageUrl,
                dateTime = LocalDateTime.now(),
            ),
        )
    }

    override fun update(productId: Long) {
    }

    override fun findOrNullByProductId(productId: Long): RecentProduct? {
        return recentProducts.firstOrNull { it.productId == productId }
    }

    override fun findMostRecentProduct(): RecentProduct? {
        return recentProducts.sortedByDescending { it.dateTime }.firstOrNull()
    }

    override fun findAll(limit: Int): List<RecentProduct> {
        val min = min(limit, recentProducts.size)
        return recentProducts.sortedByDescending { it.dateTime }.subList(0, min)
    }

    override fun deleteAll() {
        recentProducts.clear()
    }
}
