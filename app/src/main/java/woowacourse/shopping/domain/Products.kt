package woowacourse.shopping.domain

import woowacourse.shopping.ProductFixture
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Products(
    val products: List<Product> = emptyList()
) {
    @OptIn(ExperimentalUuidApi::class)
    fun findProductById(productId: Uuid): Product? = ProductFixture.productList.firstOrNull { it.productId == productId }
}