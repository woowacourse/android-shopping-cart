package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.model.PageableResponse
import woowacourse.shopping.data.model.ProductResponse
import woowacourse.shopping.data.service.ProductService

class ProductRemoteDataSourceImpl(
    private val mockProductService: ProductService,
) : ProductRemoteDataSource {
    override fun findProductById(id: Long): ProductResponse = mockProductService.findProductById(id)

    override fun findProductsByIds(ids: List<Long>): List<ProductResponse> = mockProductService.findProductsByIds(ids)

    override fun loadProducts(
        offset: Int,
        limit: Int,
    ): PageableResponse<ProductResponse> = mockProductService.loadProducts(offset, limit)
}
