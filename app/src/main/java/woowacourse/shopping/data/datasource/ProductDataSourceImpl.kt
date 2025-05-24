package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.domain.model.Product

class ProductDataSourceImpl : ProductDataSource {
    override fun getProducts(): List<Product> = DummyProducts.values

    override fun fetchProducts(): List<Product> = productService.fetchProducts()

    override fun fetchProductById(id: Long): Product = productService.fetchProductById(id)

    override fun fetchPagingProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val offset = page * pageSize
        return productService.fetchPagingProducts(offset, pageSize)
    }
}
