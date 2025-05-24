package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun findProductInfoById(
        id: Long,
        onResult: (Result<Product>) -> Unit,
    )

    fun loadProducts(
        offset: Int,
        limit: Int,
        onResult: (Result<PageableItem<Product>>) -> Unit,
    )
}
