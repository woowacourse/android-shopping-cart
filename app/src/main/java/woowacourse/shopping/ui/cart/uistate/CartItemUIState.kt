package woowacourse.shopping.ui.cart.uistate

import woowacourse.shopping.domain.CartItem

data class CartItemUIState(
    val isSelected: Boolean,
    val imageUrl: String,
    val name: String,
    val price: Int,
    val count: Int,
    val productId: Long,
) {
    companion object {
        fun create(cartItem: CartItem, isSelected: Boolean): CartItemUIState {
            val product = cartItem.product
            return CartItemUIState(
                isSelected,
                product.imageUrl,
                product.name,
                product.price,
                cartItem.count,
                product.id
            )
        }

    }
}
