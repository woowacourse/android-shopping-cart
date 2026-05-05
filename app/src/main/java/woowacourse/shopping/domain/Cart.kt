package woowacourse.shopping.domain

import java.util.Collections.emptyList
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

val CART_PAGE_SIZE = 5

@OptIn(ExperimentalUuidApi::class)
data class Cart(
    val productAndCounts: List<ProductAndCount> = emptyList(),
) {
    fun addProductToCart(product: Product): Cart {
        if (productAndCounts.any { it.hasSameProduct(product) }) {
            return copy(
                productAndCounts = productAndCounts.map { item ->
                    if (item.hasSameProduct(product)) {
                        item.increaseQuantity()
                    } else {
                        item
                    }
                }
            )
        }
        return copy(productAndCounts = productAndCounts + ProductAndCount(product, 1))
    }

    fun deleteProductFromCart(productId: Uuid): Cart =
        copy(
            productAndCounts =
                productAndCounts.filterNot {
                    it.productId == productId
                },
        )
}
