package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun findProductById(
        id: Long,
        onResult: (Result<Product>) -> Unit,
    )

    fun findProductsByIds(
        ids: List<Long>,
        onResult: (Result<List<Product>>) -> Unit,
    )

    fun loadProducts(
        offset: Int,
        loadSize: Int,
        onResult: (Result<PageableItem<Product>>) -> Unit,
    )
}
