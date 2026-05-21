package woowacourse.shopping.repository.product

import woowacourse.shopping.MockCatalog
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository
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
