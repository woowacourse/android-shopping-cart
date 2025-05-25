package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.RecentProduct

interface RecentProductRepository {
    fun insert(
        recentProduct: RecentProduct,
        onResult: (Long) -> Unit,
    )

    fun getById(
        id: Long,
        onResult: (RecentProduct) -> Unit,
    )

    fun getAll(onResult: (List<RecentProduct>) -> Unit)

    fun deleteLastByCreatedDateTime(onResult: (Unit) -> Unit)
}
