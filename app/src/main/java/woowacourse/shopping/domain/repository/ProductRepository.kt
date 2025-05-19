package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun loadProducts(
        lastItemId: Long,
        loadSize: Int,
        callback: (List<Product>, Boolean) -> Unit,
    )
}
