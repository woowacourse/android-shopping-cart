package woowacourse.shopping.domain

interface RecentRepository {
    fun load(): Result<List<RecentProductItem>>

    fun add(recentProduct: RecentProductItem): Result<Long>
}
