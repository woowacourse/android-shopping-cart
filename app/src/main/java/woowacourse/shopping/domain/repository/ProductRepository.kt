package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.product.Product

interface ProductRepository {
    fun productsByPageNumberAndSize(
        pageNumber: Int,
        loadSize: Int,
    ): List<Product>

    fun fetchById(id: Long): Product?

    fun canMoreLoad(
        pageNumber: Int,
        loadSize: Int,
    ): Boolean
}
