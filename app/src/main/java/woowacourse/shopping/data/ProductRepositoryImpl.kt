package woowacourse.shopping.data

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl : ProductRepository {
    override fun getProducts(): List<Product> = ProductDataSource.products

    override fun getProductById(id: String): Product =
        ProductDataSource.products.firstOrNull { it.id == id }
            ?: throw IllegalArgumentException("존재하지 않는 상품입니다. 삐용삐용")
}
