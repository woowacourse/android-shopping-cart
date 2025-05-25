package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.RecentProduct

interface RecentProductRepository {
    fun getLastViewedProduct(onSuccess: (RecentProduct?) -> Unit)

    fun getPagedProducts(
        limit: Int,
        offset: Int = 0,
        onSuccess: (List<RecentProduct>) -> Unit,
    )

    fun replaceRecentProduct(
        recentProduct: RecentProduct,
        onSuccess: () -> Unit,
    )
}
