package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product

interface ShoppingRepository {
    fun products(
        currentPage: Int,
        pageSize: Int,
    ): List<Product>

    fun productById(id: Long): Product?

    fun canLoadMoreProducts(
        currentPage: Int,
        pageSize: Int,
    ): Boolean

    fun updateCount(
        id: Long,
        count: Int,
    )
}
