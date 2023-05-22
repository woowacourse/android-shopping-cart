package woowacourse.shopping.data.product

import woowacourse.shopping.Product
import woowacourse.shopping.repository.ProductRepository

class ProductRepositoryImpl constructor(
    private val productRemoteDataSource: ProductRemoteDataSource,
) : ProductRepository {
    override fun findProductById(id: Int): Product {
        return productRemoteDataSource.findProductById(id)
    }

    override fun getProductsWithRange(startIndex: Int, size: Int): List<Product> {
        return productRemoteDataSource.getProductsWithRange(startIndex, size)
    }
}
