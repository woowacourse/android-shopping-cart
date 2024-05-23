package woowacourse.shopping.model.data

import woowacourse.shopping.model.RecentProduct

object RecentProductsImpl : RecentProductDao {
    private const val EXCEPTION_INVALID_ID = "RecentProduct not found with id: %d"
    private var id: Long = 0
    private val recentProducts = mutableMapOf<Long, RecentProduct>()

    override fun save(recentProduct: RecentProduct): Long {
        recentProducts[id] = recentProduct.copy(id = id)
        return id++
    }

    override fun deleteAll() {
        recentProducts.clear()
    }

    override fun find(id: Long): RecentProduct {
        return recentProducts[id] ?: throw NoSuchElementException(invalidIdMessage(id))
    }

    override fun findAll(): List<RecentProduct> {
        return recentProducts.map { it.value }
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
