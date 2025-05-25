package woowacourse.shopping.data.recent

import woowacourse.shopping.domain.RecentProduct

interface RecentProductRepository {
    fun getMostRecent(
        count: Int,
        onSuccess: (List<RecentProduct>) -> Unit,
    )

    fun getLastProductBefore(
        productId: Int,
        onResult: (RecentProduct?) -> Unit,
    )

    fun insert(recentProduct: RecentProduct)
}
