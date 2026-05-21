package woowacourse.shopping.repository.productRepository

import woowacourse.shopping.MockCatalog
import woowacourse.shopping.domain.Product
import java.util.UUID

object CatalogProductRepository : ProductRepository {
    val catalog = MockCatalog
    override suspend fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        return catalog.loadProducts(page, pageSize).await()
    }

    override suspend fun getProductById(id: UUID): Product = catalog.findProductById(id)
}
