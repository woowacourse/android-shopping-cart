package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.CatalogProducts

interface ProductRepository {
    fun fetchProducts(
        lastId: Int,
        count: Int,
        callback: (CatalogProducts) -> Unit = {},
    )

    fun fetchProductDetail(
        id: Int,
        callback: (CartProduct) -> Unit,
    )
}
