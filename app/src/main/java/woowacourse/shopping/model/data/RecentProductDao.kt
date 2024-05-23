package woowacourse.shopping.model.data

import woowacourse.shopping.model.RecentProduct

interface RecentProductDao {
    fun save(recentProduct: RecentProduct): Long

    fun find(id: Long): RecentProduct

    fun findAll(): List<RecentProduct>

    fun deleteAll()
}
