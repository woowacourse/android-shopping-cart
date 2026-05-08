package woowacourse.shopping.domain.model.cart

import woowacourse.shopping.domain.model.product.Product
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Cart(
    val cartItems: List<CartItem> = emptyList(),
) {
    fun getTotalQuantity(): Int = cartItems.sumOf { it.quantity }

    fun increaseQuantity(
        product: Product,
        quantity: Int,
    ): Cart {
        val exists = cartItems.any { product.productId == it.product.productId }

        if (!exists) {
            return copy(cartItems = cartItems + CartItem(product, 1))
        }

        return copy(
            cartItems =
                cartItems.map {
                    if (product.productId == it.product.productId) {
                        it.increaseQuantity(quantity)
                    } else {
                        it
                    }
                },
        )
    }

    fun decreaseQuantity(productId: Uuid): Cart {
        val cartItem = cartItems.find { it.product.productId == productId } ?: return this

        if (cartItem.quantity == 1) {
            return copy(
                cartItems =
                    cartItems.filterNot {
                        it.product.productId == productId
                    },
            )
        }

        return copy(
            cartItems =
                cartItems.map {
                    if (it.product.productId == productId) {
                        it.decreaseQuantity()
                    } else {
                        it
                    }
                },
        )
    }

    fun deleteProduct(productId: Uuid): Cart =
        copy(
            cartItems =
                cartItems.filterNot {
                    it.product.productId == productId
                },
        )
}
