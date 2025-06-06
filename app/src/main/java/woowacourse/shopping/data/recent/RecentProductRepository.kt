package woowacourse.shopping.data.recent

import woowacourse.shopping.domain.RecentItem

interface RecentProductRepository {
    fun getMostRecent(
        count: Int,
        onSuccess: (List<RecentItem>) -> Unit,
    )

    fun getLastProductBefore(
        productId: Int,
        onResult: (RecentItem?) -> Unit,
    )

    fun insert(recentProduct: RecentItem)

    fun clear()
}
