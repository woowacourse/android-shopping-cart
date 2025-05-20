package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductSinglePage

interface ProductRepository {
    operator fun get(id: Long): Product

    fun modifyQuantity(
        productId: Long,
        quantity: Quantity,
    )

    fun loadSinglePage(
        page: Int,
        pageSize: Int,
    ): ProductSinglePage
}
