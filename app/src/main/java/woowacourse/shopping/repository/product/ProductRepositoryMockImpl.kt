package woowacourse.shopping.repository.product

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.mock.MockData

class ProductRepositoryMockImpl : ProductRepository {
    override suspend fun getProductsSize(): Int = MockData.products.size

    override suspend fun getProduct(id: String): Product = MockData.products.find { it.id == id }!!

    override suspend fun getPagedProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> = MockData.products.subList(page * pageSize, minOf(MockData.products.size, (page + 1) * pageSize))
}
