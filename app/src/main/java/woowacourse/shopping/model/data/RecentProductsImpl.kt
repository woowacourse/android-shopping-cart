package woowacourse.shopping.model.data

import woowacourse.shopping.model.RecentProduct
import java.time.LocalTime

object RecentProductsImpl : RecentProductDao {
    private const val EXCEPTION_INVALID_ID = "RecentProduct not found with id: %d"
    private const val RECENT_PRODUCT_MAX_SIZE = 10
    private var id: Long = 0
    private val recentProducts = mutableMapOf<Long, RecentProduct>()

    override fun save(productId: Long): Long {
        val recentTime = LocalTime.now()
        val oldRecentProduct = recentProducts.values.find { it.productId == productId }

        if (oldRecentProduct == null) { // 이미 저장된 최근 본 상품이 아닌 경우
            removeOldestRecentProduct()
            saveNewRecentProduct(productId, recentTime)
            return id++
        }
        recentProducts[oldRecentProduct.id] = oldRecentProduct.copy(recentTime = recentTime)
        return oldRecentProduct.id
    }

    override fun deleteAll() {
        recentProducts.clear()
    }

    override fun find(id: Long): RecentProduct {
        return recentProducts[id] ?: throw NoSuchElementException(invalidIdMessage(id))
    }

    override fun findMostRecentProduct(): RecentProduct {
        return recentProducts.values.maxBy { it.recentTime }
    }

    override fun findAll(): List<RecentProduct> {
        return recentProducts.values.sortedByDescending { it.recentTime }
    }

    private fun saveNewRecentProduct(
        productId: Long,
        recentTime: LocalTime,
    ) {
        recentProducts[id] = RecentProduct(id = id, productId = productId, recentTime = recentTime)
    }

    private fun removeOldestRecentProduct() {
        if (recentProducts.size >= RECENT_PRODUCT_MAX_SIZE) {
            val oldestProduct = recentProducts.values.minBy { it.recentTime }
            recentProducts.remove(oldestProduct.id)
        }
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
