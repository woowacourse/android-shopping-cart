package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.Products

interface ProductRepository {
    fun fetchProducts(
        lastId: Int,
        count: Int,
        callback: (Products) -> Unit = {},
    )

    fun fetchProductDetail(
        id: Int,
        callback: (Product) -> Unit,
    )
}
