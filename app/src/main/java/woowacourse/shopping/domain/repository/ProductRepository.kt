package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductResponse

interface ProductRepository {
    fun loadPagingProducts(
        offset: Int,
        pagingSize: Int,
    ): ProductResponse

    fun getProduct(productId: Long): Product
}
