package woowacourse.shopping.data.cart

import woowacourse.shopping.domain.cart.model.Cart
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.model.CartItemQuantity
import woowacourse.shopping.domain.cart.model.CartItems
import woowacourse.shopping.domain.cart.repository.CartRepository

class CartRepositoryMockImpl : CartRepository {
    private var cart: Cart = Cart(cartItems = CartItems(mutableListOf()))

    override fun getCart(): Cart = cart

    override fun getTotalCartCount(): Int = cart.getCartSize()

    override fun addCartItem(cartItem: CartItem, targetQuantity: Int) {
        cart = cart.addCartItem(cartItem, targetQuantity)
    }

    override fun minusCartItem(cartItem: CartItem, targetQuantity: Int) {
        cart = cart.minusCartItem(cartItem, targetQuantity)
    }

    override fun removeCartItem(cartItem: CartItem) {
        cart = cart.removeCartItem(cartItem)
    }

    override fun isCartItemExist(cartItem: CartItem): Boolean {
        return cart.searchCartItem(cartItem)
    }

}
