package woowacourse.shopping.data.product

import woowacourse.shopping.data.MockData
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.repository.ProductRepository

class ProductRepositoryMockImpl : ProductRepository {
    override fun getProductsSize(): Int = MockData.products.size

    override fun getProduct(id: String): Product = MockData.products.find { it.id == id }!!

    override fun isProductExist(productId: String): Boolean = MockData.products.any { it.id == productId }

    override fun getPagedProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> = MockData.products.subList(page * pageSize, minOf(MockData.products.size, (page + 1) * pageSize))
}
