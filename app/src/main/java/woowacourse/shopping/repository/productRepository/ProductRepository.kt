package woowacourse.shopping.repository.productRepository

import woowacourse.shopping.domain.Product
import java.util.UUID

interface ProductRepository {
    suspend fun getProducts(page: Int, pageSize: Int): List<Product>
    suspend fun getProductById(id: UUID): Product?
}
