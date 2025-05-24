package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.remote.ProductService
import woowacourse.shopping.domain.model.Product

class ProductDataSourceImpl(
    private val productService: ProductService,
) : ProductDataSource {
    override fun start() = productService.start()

    override fun fetchProducts(): List<Product> = productService.fetchProducts()

    override fun fetchProductById(id: Long): Product = productService.fetchProductById(id)

    override fun fetchPagingProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val offset = page * pageSize
        return productService.fetchPagingProducts(offset, pageSize)
    }

    override fun shutdown() = productService.shutdown()
}
