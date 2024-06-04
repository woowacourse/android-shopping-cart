package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.RecentProductItem

interface RecentRepository {
    fun loadAll(): Result<List<RecentProductItem>>

    fun loadMostRecent(): Result<RecentProductItem?>

    fun add(recentProduct: RecentProductItem): Result<Long>
}
