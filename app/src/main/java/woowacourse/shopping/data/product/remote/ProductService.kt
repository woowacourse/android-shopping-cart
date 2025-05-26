package woowacourse.shopping.data.product.remote

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.domain.model.Product

interface ProductService {
    fun getProductById(id: Long): Product?

    fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<Product>?
}
