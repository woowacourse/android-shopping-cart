package woowacourse.shopping.repository

import woowacourse.shopping.MockCatalog
import woowacourse.shopping.domain.Product
import java.util.UUID

class CatalogProductRepository(
    private val catalog: MockCatalog
): ProductRepository {
    override suspend fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        return catalog.loadProducts(page, pageSize).await()
    }

    override fun getProductById(id: UUID): Product = catalog.findProductById(id)
}
