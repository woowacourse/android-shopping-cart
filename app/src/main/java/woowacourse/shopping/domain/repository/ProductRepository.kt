package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun findProductById(
        id: Long,
        callback: (Product?) -> Unit,
    )

    fun findProductsByIds(
        ids: List<Long>,
        callback: (List<Product>) -> Unit,
    )

    fun loadProducts(
        lastItemId: Long,
        loadSize: Int,
        callback: (List<Product>, Boolean) -> Unit,
    )
}
