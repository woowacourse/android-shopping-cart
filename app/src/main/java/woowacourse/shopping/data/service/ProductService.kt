package woowacourse.shopping.data.service

import woowacourse.shopping.domain.model.Product

interface ProductService {
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
