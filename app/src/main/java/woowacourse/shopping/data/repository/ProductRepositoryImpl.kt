package woowacourse.shopping.data.repository

import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.ProductDataSourceImpl
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource = ProductDataSourceImpl,
) : ProductRepository {
    override fun getProducts(): List<Product> = productDataSource.products

    override fun getProductById(id: String): Product =
        productDataSource.products.firstOrNull { it.id == id }
            ?: throw IllegalArgumentException("존재하지 않는 상품입니다. 삐용삐용")
}
