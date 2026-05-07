package woowacourse.shopping.domain

import java.util.Collections.emptyList
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

val CART_PAGE_SIZE = 5

@OptIn(ExperimentalUuidApi::class)
data class Cart(
    val productsWithQuantity: List<ProductWithQuantity> = emptyList(),
) {
    fun addProductToCart(productWithQuantityToAdd: ProductWithQuantity): Cart {
        if (productsWithQuantity.any { it.hasSameProduct(productWithQuantityToAdd) }) {
            return copy(
                productsWithQuantity = productsWithQuantity.map { item ->
                    if (item.hasSameProduct(productWithQuantityToAdd)) {
                        item.increaseQuantity(productWithQuantityToAdd.quantity)
                    } else {
                        item
                    }
                }
            )
        }
        return copy(
            productsWithQuantity = productsWithQuantity + ProductWithQuantity(
                productWithQuantityToAdd.product,
                productWithQuantityToAdd.quantity
            )
        )
    }

    fun deleteProductFromCart(productId: Uuid): Cart =
        copy(
            productsWithQuantity =
                productsWithQuantity.filterNot {
                    it.productId == productId
                },
        )
}
