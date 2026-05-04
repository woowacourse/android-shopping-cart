package woowacourse.shopping.repository.product

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.Products
import woowacourse.shopping.mock.MockData

class MockProductRepository : ProductRepository {
    private val products = Products(MockData.products)

    override suspend fun getProducts(page: Int, pageSize: Int): List<Product> =
        products.getPage(page, pageSize)

    override suspend fun getProduct(id: String): Product? =
        MockData.products.find { it.id == id } ?: MockData.productInfo
}
