package woowacourse.shopping.domain

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Cart(
    val productAndCounts: List<ProductAndCount> = emptyList(),
) {
    fun addProduct(product: Product): Cart = copy(productAndCounts = productAndCounts + ProductAndCount(product, 1))

    fun deleteProduct(productId: Uuid): Cart =
        copy(
            productAndCounts =
                productAndCounts.filterNot {
                    it.productId == productId
                },
        )
}
