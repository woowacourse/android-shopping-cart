package woowacourse.shopping.ui.cart.uistate

import woowacourse.shopping.domain.CartItem

data class CartItemUIState(
    val selected: Boolean,
    val imageUrl: String,
    val name: String,
    val price: Int,
    val productId: Long,
) {
    companion object {
        fun from(cartItem: CartItem): CartItemUIState {
            val product = cartItem.product
            return CartItemUIState(false, product.imageUrl, product.name, product.price, product.id)
        }

    }
}
