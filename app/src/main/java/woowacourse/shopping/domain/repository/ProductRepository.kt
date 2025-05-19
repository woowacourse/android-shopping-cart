package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun findProductById(id: Long): Result<Product>

    fun findProductsByIds(ids: List<Long>): Result<List<Product>>

    fun loadProducts(
        offset: Int,
        loadSize: Int,
    ): Result<PageableItem<Product>>
}
