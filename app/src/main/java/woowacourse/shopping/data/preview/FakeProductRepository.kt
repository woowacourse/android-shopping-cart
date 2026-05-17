package woowacourse.shopping.data.preview

import woowacourse.shopping.data.mock.MockProductSeedData
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.Products
import woowacourse.shopping.domain.repository.ProductRepository

class FakeProductRepository : ProductRepository {
    private val products = Products(MockProductSeedData.products)

    override suspend fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> = products.getPage(page, pageSize)

    override suspend fun getProduct(id: Int): Product? = MockProductSeedData.products.find { it.id == id }
}
