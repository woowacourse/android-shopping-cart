package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.cart.CartItems
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.math.min

object CartRepositoryImpl : CartRepository {
    private var currentCart = CartItems()

    override fun getCartItems(): CartItems = currentCart

    override fun getCartItem(productId: String): CartItem? =
        currentCart.items.find { it.product.id == productId }

    override fun addCartItem(cartItem: CartItem) {
        currentCart = currentCart.addOrMerge(cartItem)
    }

    override fun deleteCartItem(productId: String) {
        currentCart = currentCart.remove(productId)
    }

    override fun getCartItemCount(): Int = currentCart.items.size

    override fun getPagingCartItems(
        page: Int,
        pageSize: Int,
    ): CartItems {
        require(page >= 0) { "페이지 번호는 0보다 크거나 같은 정수여야 합니다." }
        require(pageSize >= 1) { "페이지 사이즈는 1보다 큰 정수여야 합니다." }
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, currentCart.items.size)

        require(fromIndex <= toIndex) { "끝 인덱스는 시작 인덱스보다 작거나 같아야합니다." }

        val result = currentCart.items.subList(fromIndex, toIndex)

        return CartItems(result)
    }

    override fun saveCartItems(cartItems: CartItems) {
        currentCart = cartItems
    }
}
