package woowacourse.shopping.data

import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.repository.RecentProductRepository
import java.time.LocalDateTime

class FakeRecentProductRepository : RecentProductRepository {
    private val recentProducts = mutableListOf(
        RecentProduct(
            Product(1L, "맥북", Price(1000), ""),
            LocalDateTime.of(2024, 5, 1, 12, 0)
        ),
        RecentProduct(
            Product(2L, "아이폰", Price(2000), ""),
            LocalDateTime.of(2024, 5, 2, 12, 0)
        ),
        RecentProduct(
            Product(3L, "에어팟", Price(3000), ""),
            LocalDateTime.of(2024, 5, 3, 12, 0)
        ),
        RecentProduct(
            Product(4L, "매직키보드", Price(4000), ""),
            LocalDateTime.of(2024, 5, 4, 12, 0)
        ),
        RecentProduct(
            Product(5L, "에어팟맥스", Price(5000), ""),
            LocalDateTime.of(2024, 5, 5, 12, 0)
        ),
        RecentProduct(
            Product(6L, "에어팟깁스", Price(6000), ""),
            LocalDateTime.of(2024, 5, 6, 12, 0)
        )
    )

    private var nextId = 1L

    override fun insert(recentProduct: RecentProduct, onResult: (Long) -> Unit) {
        recentProducts.add(recentProduct)
        onResult(nextId++)
    }

    override fun getById(id: Long, onResult: (RecentProduct?) -> Unit) {
        val product = recentProducts.find { it.product.id == id }
        onResult(product)
    }

    override fun getLatest(onResult: (RecentProduct?) -> Unit) {
        val latest = recentProducts.maxByOrNull { it.createdDateTime }
        onResult(latest)
    }

    override fun getAll(onResult: (List<RecentProduct>) -> Unit) {
        onResult(recentProducts.toList())
    }

    override fun deleteLastByCreatedDateTime(onResult: (Unit) -> Unit) {
        val latest = recentProducts.maxByOrNull { it.createdDateTime }
        if (latest != null) {
            recentProducts.remove(latest)
        }
        onResult(Unit)
    }
}