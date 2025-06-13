package woowacourse.shopping.data.recentlyproducts

import woowacourse.shopping.domain.Product

interface RecentlyProductsRepository {
    fun insert(
        product: Product,
        onLoad: (Result<Unit>) -> Unit,
    )

    fun getFirst(onLoad: (Result<Long?>) -> Unit)

    fun getAll(onLoad: (Result<List<Long>?>) -> Unit)

    fun deleteMostRecent(onLoad: (Result<Unit>) -> Unit)
}
