package woowacourse.shopping.ui.cart.uistate

import woowacourse.shopping.domain.CartItem

data class CartItemUIState(
    val isSelected: Boolean,
    val imageUrl: String,
    val name: String,
    val price: Int,
    val count: Int,
    val id: Long
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
                cartItem.id
                    ?: throw IllegalArgumentException("아이디가 부여되지 않은 장바구니 아이템은 UI 상태가 될 수 없습니다.")
            )
        }

    }
}
