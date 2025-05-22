package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductSinglePage

interface ProductRepository {
    operator fun get(
        productId: Long,
        onResult: (Product) -> Unit,
    )

    fun loadSinglePage(
        page: Int,
        pageSize: Int,
        onResult: (ProductSinglePage) -> Unit,
    )
}
