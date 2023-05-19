package woowacourse.shopping.presentation.view.cart

import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.data.respository.cart.CartRepository

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private var currentPage: Int = 1
) : CartContract.Presenter {
    private val carts =
        cartRepository.getAllCarts().map { it.toUIModel().apply { product.count = it.count } }
            .toMutableList()

    private val startPosition: Int
        get() = (currentPage - 1) * DISPLAY_CART_COUNT_CONDITION

    override fun loadCartItems() {
        val subToIndex =
            if (carts.size > startPosition + DISPLAY_CART_COUNT_CONDITION) startPosition + DISPLAY_CART_COUNT_CONDITION else carts.size
        val newCarts = carts.subList(startPosition, subToIndex)

        view.setEnableLeftButton(currentPage != FIRST_PAGE_NUMBER)
        view.setEnableRightButton(carts.size > startPosition + DISPLAY_CART_COUNT_CONDITION)

        view.setCartItemsView(newCarts)
    }

    override fun deleteCartItem(itemId: Long) {
        cartRepository.deleteCartByCartId(itemId)
        carts.removeIf { it.id == itemId }
        loadCartItems()
    }

    override fun calculatePreviousPage() {
        view.setPageCountView(--currentPage)
    }

    override fun calculateNextPage() {
        view.setPageCountView(++currentPage)
    }

    override fun updateProductCount(cartId: Long, count: Int) {
        actionToZeroCount(cartId, count)
        cartRepository.updateCartByCartId(cartId, count)
    }

    private fun actionToZeroCount(cartId: Long, count: Int) {
        if (count == 0) {
            cartRepository.deleteCartByCartId(cartId)
            loadCartItems()
        }
    }

    companion object {
        private const val FIRST_PAGE_NUMBER = 1
        private const val DISPLAY_CART_COUNT_CONDITION = 3
    }
}
