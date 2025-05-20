package woowacourse.shopping.data.repository

import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.domain.Product

class ProductsRepositoryImpl : ProductsRepository {
    override fun findAll(): List<Product> {
        return DummyProducts.products.distinct()
    }
}
