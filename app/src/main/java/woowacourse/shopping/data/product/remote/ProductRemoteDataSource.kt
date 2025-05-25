package woowacourse.shopping.data.product.remote

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.domain.model.Product

class ProductRemoteDataSource(
    private val service: ProductService,
) {
    fun getProductById(id: Long): Product? = service.getProductById(id)

    fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<Product> = service.getPagedProducts(limit, offset)
}
