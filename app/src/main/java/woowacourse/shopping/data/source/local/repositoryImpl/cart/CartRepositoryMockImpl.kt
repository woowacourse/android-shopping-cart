package woowacourse.shopping.data.source.local.repositoryImpl.cart

import woowacourse.shopping.domain.cart.model.Cart
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.model.CartItems
import woowacourse.shopping.domain.cart.repository.CartRepository

class CartRepositoryMockImpl : CartRepository {
    private var cart: Cart = Cart(cartItems = CartItems(mutableListOf()))

    override suspend fun getCart(): Cart = cart

    override suspend fun getTotalCartCount(): Int = cart.getCartSize()

    override suspend fun getTotalCartItemCount(): Int = cart.getTotalCartItemCount()

    override suspend fun getQuantity(cartItem: CartItem): Int = cart.getQuantity(cartItem)

    override suspend fun addCartItem(
        cartItem: CartItem,
    ) {
        cart = cart.addCartItem(cartItem)
    }

    override suspend fun minusCartItem(
        cartItem: CartItem,
    ) {
        cart = cart.minusCartItem(cartItem)
    }

    override suspend fun removeCartItem(cartItem: CartItem) {
        cart = cart.removeCartItem(cartItem)
    }

    override suspend fun isCartItemExist(cartItem: CartItem): Boolean = cart.searchCartItem(cartItem)
}
