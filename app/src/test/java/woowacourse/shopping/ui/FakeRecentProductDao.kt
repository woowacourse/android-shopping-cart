package woowacourse.shopping.ui

import woowacourse.shopping.model.data.RecentProductDao
import woowacourse.shopping.model.data.RecentProductEntity

object FakeRecentProductDao : RecentProductDao {
    private var id: Long = 0
    private val recentProducts = mutableMapOf<Long, RecentProductEntity>()

    override fun getAll(): List<RecentProductEntity> {
        return recentProducts.map { it.value }
    }

    override fun getSecondLast(): RecentProductEntity? {
        return recentProducts[(recentProducts.size - 2).toLong()]
    }

    override fun insert(recentProduct: RecentProductEntity) {
        recentProducts[id] = recentProduct
        id++
    }
}
