package woowacourse.shopping.data.product

import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.repository.ProductRepository
import woowacourse.shopping.data.MockData

class ProductRepositoryMockImpl : ProductRepository {
    override suspend fun getProductsSize(): Int = MockData.products.size

    override suspend fun getProduct(id: String): Product = MockData.products.find { it.id == id }!!

    override suspend fun getPagedProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> = MockData.products.subList(page * pageSize, minOf(MockData.products.size, (page + 1) * pageSize))
}
