package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun findProductById(id: Long): Result<Product>

    fun getPagingProduct(
        page: Int,
        pageSize: Int,
    ): Result<List<Product>>

    fun shutdown(): Result<Unit>
}
