package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductResult

interface ProductRepository {
    operator fun get(id: Long): Product

    fun loadSinglePage(
        page: Int,
        pageSize: Int,
    ): ProductResult
}
