package woowacourse.shopping.ui

import woowacourse.shopping.data.recentproduct.RecentProduct
import woowacourse.shopping.data.recentproduct.RecentProductDao
import java.time.LocalDateTime

object FakeRecentProductDao : RecentProductDao {
    private var id: Long = 0
    private val recentProducts = mutableMapOf<Long, RecentProduct>()

    override fun insert(recentProduct: RecentProduct): Long {
        val recentTime = LocalDateTime.now()
        val oldRecentProduct =
            recentProducts.values.find { it.productId == recentProduct.productId }

        if (oldRecentProduct == null) { // 이미 저장된 최근 본 상품이 아닌 경우
            recentProducts[id] =
                RecentProduct(id = id, productId = recentProduct.productId, recentTime = recentTime)
            return id++
        }
        recentProducts[oldRecentProduct.id] = oldRecentProduct.copy(recentTime = recentTime)
        return oldRecentProduct.id
    }

    override fun deleteAll() {
        recentProducts.clear()
    }

    override fun findMostRecentProduct(): RecentProduct? {
        if (recentProducts.isEmpty()) {
            return null
        }
        return recentProducts.values.maxBy { it.recentTime }
    }

    override fun findAll(): List<RecentProduct> {
        return recentProducts.values.sortedByDescending { it.recentTime }
    }
}
