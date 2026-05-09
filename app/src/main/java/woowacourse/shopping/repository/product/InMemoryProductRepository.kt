package woowacourse.shopping.repository.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class InMemoryProductRepository(
    packageName: String,
) : ProductRepository {
    private var products by mutableStateOf(Products(ProductFixture.productList(packageName)))
    private val productsFlow = MutableStateFlow(products)

    override fun getAllProducts(): Flow<Products> = productsFlow.asStateFlow()

    override suspend fun getProductById(productId: Uuid): Product? = products.findProductById(productId)

    override suspend fun refreshProducts() = Unit
}
