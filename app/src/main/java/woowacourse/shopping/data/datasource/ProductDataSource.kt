package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product

interface ProductDataSource {
    fun loadProducts(
        offset: Int,
        loadSize: Int,
    ): Result<PageableItem<Product>>

    fun findProductById(id: Long): Result<Product>

    fun findProductsByIds(ids: List<Long>): Result<List<Product>>
}
