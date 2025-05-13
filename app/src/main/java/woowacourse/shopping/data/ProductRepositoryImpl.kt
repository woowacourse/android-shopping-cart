package woowacourse.shopping.data

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository

class ProductRepositoryImpl : ProductRepository {
    override fun getProducts(): List<Product> = DummyProducts.values
}
