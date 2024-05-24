package woowacourse.shopping.domain

interface RecentRepository {
    fun loadAll(): Result<List<RecentProductItem>>

    fun loadLast(): Result<RecentProductItem?>

    fun add(recentProduct: RecentProductItem): Result<Long>
}
