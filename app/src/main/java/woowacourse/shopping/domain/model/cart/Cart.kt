package woowacourse.shopping.domain.model.cart

import woowacourse.shopping.domain.model.product.Product
import java.util.Collections.emptyList
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Cart(
    val cartItems: List<CartItem> = emptyList(),
) {
    fun addProductToCart(product: Product): Cart {
        val exists = cartItems.any { product.productId == it.product.productId }

        if (!exists) {
            return copy(cartItems = cartItems + CartItem(product, 1))
        }

        return copy(
            cartItems =
                cartItems.map {
                    if (product.productId == it.product.productId) {
                        it.increaseQuantity()
                    } else {
                        it
                    }
                },
        )
    }

    fun deleteProductFromCart(productId: Uuid): Cart =
        copy(
            cartItems =
                cartItems.filterNot {
                    it.productId == productId
                },
        )
}
