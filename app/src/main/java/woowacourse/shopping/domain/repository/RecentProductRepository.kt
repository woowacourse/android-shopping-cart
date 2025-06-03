package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.RecentProduct

interface RecentProductRepository {
    fun getLastViewedProduct(onResult: (Result<RecentProduct?>) -> Unit)

    fun getPagedProducts(
        limit: Int,
        offset: Int = 0,
        onResult: (Result<List<RecentProduct>>) -> Unit,
    )

    fun replaceRecentProduct(
        recentProduct: RecentProduct,
        onResult: (Result<Unit>) -> Unit,
    )
}
