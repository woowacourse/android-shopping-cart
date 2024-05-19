package woowacourse.shopping.data.shopping

import woowacourse.shopping.domain.Product

interface ShoppingDataSource {
    fun products(
        currentPage: Int,
        pageSize: Int,
    ): List<Product>

    fun productById(id: Long): Product?

    fun canLoadMoreProducts(
        currentPage: Int,
        pageSize: Int,
    ): Boolean
}
