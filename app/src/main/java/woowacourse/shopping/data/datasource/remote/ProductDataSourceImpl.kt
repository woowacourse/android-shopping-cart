package woowacourse.shopping.data.datasource.remote

import woowacourse.shopping.data.remote.ProductService
import woowacourse.shopping.domain.model.Product

class ProductDataSourceImpl(
    private val productService: ProductService,
) : ProductDataSource {
    override fun fetchPagingProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val offset = page * pageSize
        return productService.fetchPagingProducts(offset, pageSize)
    }

    override fun fetchProductById(id: Long): Product = productService.fetchProductById(id)
}
