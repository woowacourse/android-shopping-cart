package woowacourse.shopping.data.recentlyproducts

import woowacourse.shopping.domain.Product

interface RecentlyProductsRepository {
    fun insert(product: Product)

    fun getFirst(): Long?

    fun getAll(): List<Long>?
}
