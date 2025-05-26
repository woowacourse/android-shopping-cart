package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.model.PageableResponse
import woowacourse.shopping.data.model.ProductResponse
import woowacourse.shopping.data.service.ProductService

class ProductRemoteDataSourceImpl(
    private val productService: ProductService,
) : ProductRemoteDataSource {
    override fun findProductById(id: Long): ProductResponse = productService.findProductById(id)

    override fun findProductsByIds(ids: List<Long>): List<ProductResponse> = productService.findProductsByIds(ids)

    override fun loadProducts(
        offset: Int,
        limit: Int,
    ): PageableResponse<ProductResponse> = productService.loadProducts(offset, limit)
}
