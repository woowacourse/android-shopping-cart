package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class DefaultProductRepository(private val productDataSource: ProductDataSource) :
    ProductRepository {
    private var page: Int = 0

    override fun fetchCurrentPage(): List<Product> {
        return productDataSource.getProducts(page, PAGE_SIZE)
    }

    override fun fetchNextPage(): List<Product> {
        return productDataSource.getProducts(page++, PAGE_SIZE)
    }

    override fun fetchProduct(id: Long): Product {
        return productDataSource.getProductById(id)
    }

    override fun fetchProducts(ids: List<Long>): List<Product> {
        return productDataSource.getProductByIds(ids)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
