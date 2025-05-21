package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.model.Product

interface ProductDataSource {
    fun findProductById(id: Long): Product

    fun findProductsByIds(ids: List<Long>): List<Product>

    fun loadProducts(
        offset: Int,
        limit: Int,
    ): List<Product>

    fun calculateHasMore(
        offset: Int,
        limit: Int,
    ): Boolean
}
