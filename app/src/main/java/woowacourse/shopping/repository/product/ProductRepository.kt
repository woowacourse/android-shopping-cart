package woowacourse.shopping.repository.product

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface ProductRepository {
    fun getAllProducts(): Flow<Products>

    suspend fun getProductById(productId: Uuid): Product?

    suspend fun refreshProducts()
}
