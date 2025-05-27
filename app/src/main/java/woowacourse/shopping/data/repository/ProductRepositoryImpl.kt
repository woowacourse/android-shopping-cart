package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource,
) : ProductRepository {
    override fun getById(id: Long) = productDataSource[id]

    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> = productDataSource.getProducts(page, pageSize)

    override fun notHasMoreProduct(
        page: Int,
        pageSize: Int,
    ): Boolean = productDataSource.notHasMoreProduct(page, pageSize)
}
