package woowacourse.shopping.repository.product

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import woowacourse.shopping.MockCatalog
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository
import java.util.UUID

class InMemoryProductRepository(
    private val products: List<Product> = MockCatalog.catalog,
) : ProductRepository {
    override suspend fun getProducts(page: Int, pageSize: Int): List<Product> =
        withContext(Dispatchers.Default) {
            require(page >= 0)
            require(pageSize > 0)

            val fromIndex = (page * pageSize).coerceAtMost(products.size)
            val toIndex = (fromIndex + pageSize).coerceAtMost(products.size)

            products.subList(fromIndex, toIndex)
        }

    override suspend fun getProductById(id: UUID): Product? =
        products.find { it.productId == id }
}
