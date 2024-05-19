package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(private val productDataSource: ProductDataSource) : ProductRepository {
    override fun fetchSinglePage(page: Int): List<Product> {
        return productDataSource.getProducts(page, PAGE_SIZE)
    }

    override fun fetchProduct(id: Long): Product {
        return productDataSource.getProductById(id)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
