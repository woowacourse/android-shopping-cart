package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CatalogProduct
import woowacourse.shopping.domain.model.CatalogProducts

interface ProductRepository {
    fun fetchProducts(
        lastId: Int,
        count: Int,
        callback: (CatalogProducts) -> Unit = {},
    )

    fun fetchProduct(
        productId: Int,
        callback: (CatalogProduct?) -> Unit,
    )

    fun fetchProducts(
        productIds: List<Int>,
        callback: (List<CatalogProduct>) -> Unit,
    )
}
