package woowacourse.shopping.data.recentlyproducts

import woowacourse.shopping.domain.Product

interface RecentlyProductsRepository {
    fun insert(
        product: Product,
        callback: (() -> Unit)?,
    )

    fun getFirst(callback: (Long?) -> Unit)

    fun getAll(callback: (List<Long>?) -> Unit)

    fun deleteMostRecent()
}
