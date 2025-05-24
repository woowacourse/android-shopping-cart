package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.service.ProductService
import woowacourse.shopping.domain.model.Product

class ProductDataSourceImpl(
    private val mockProductService: ProductService,
) : ProductDataSource {
    override fun findProductById(id: Long): Product = mockProductService.findProductById(id)

    override fun findProductsByIds(ids: List<Long>): List<Product> = mockProductService.findProductsByIds(ids)

    override fun loadProducts(
        offset: Int,
        limit: Int,
    ): List<Product> = mockProductService.loadProducts(offset, limit)

    override fun calculateHasMore(
        offset: Int,
        limit: Int,
    ): Boolean = mockProductService.calculateHasMore(offset, limit)
}
