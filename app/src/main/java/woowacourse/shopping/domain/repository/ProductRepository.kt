package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.CatalogProducts

interface ProductRepository {
    fun fetchProducts(
        lastId: Int,
        count: Int,
        callback: (CatalogProducts) -> Unit = {},
    )

    fun fetchProduct(
        productId: Int,
        callback: (CartProduct?) -> Unit,
    )

    fun fetchProducts(
        productIds: List<Int>,
        callback: (List<CartProduct>) -> Unit,
    )
}
