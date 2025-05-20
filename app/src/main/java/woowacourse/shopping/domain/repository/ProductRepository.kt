package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductSinglePage

interface ProductRepository {
    operator fun get(id: Long): Product

    fun modifyQuantity(
        id: Long,
        quantity: Int,
    )

    fun loadSinglePage(
        page: Int,
        pageSize: Int,
    ): ProductSinglePage
}
