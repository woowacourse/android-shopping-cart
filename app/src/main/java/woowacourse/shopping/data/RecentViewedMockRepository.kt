package woowacourse.shopping.data

import woowacourse.shopping.domain.RecentViewedRepository

object RecentViewedMockRepository : RecentViewedRepository {
    private val productIds = listOf(0, 1, 2)

    override fun findAll(): List<Int> {
        return productIds
    }

    override fun add(id: Int) {
    }

    override fun remove(id: Int) {
    }
}
