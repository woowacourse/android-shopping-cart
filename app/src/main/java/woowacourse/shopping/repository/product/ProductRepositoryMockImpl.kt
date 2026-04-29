package woowacourse.shopping.repository.product

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.mock.MockData

class ProductRepositoryMockImpl : ProductRepository {
    override fun getProducts(): List<Product> = MockData.products
    override fun getProduct(id: String?): Product {
        return MockData.products.first { it.id == id } ?: MockData.productInfo
    }
}
