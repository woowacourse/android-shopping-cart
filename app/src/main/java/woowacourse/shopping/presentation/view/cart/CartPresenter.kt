package woowacourse.shopping.presentation.view.cart

import woowacourse.shopping.data.respository.cart.CartRepository
import woowacourse.shopping.data.respository.cart.CartTotalPriceRepository
import woowacourse.shopping.domain.CartPage
import woowacourse.shopping.presentation.model.CartProductModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private val cartTotalPriceRepository: CartTotalPriceRepository,
    private var cartPage: CartPage = CartPage()
) : CartContract.Presenter {
    override fun loadCartItems() {
        var newCarts = getNewCarts()
        view.setEnableLeftButton(cartPage.currentPage != FIRST_PAGE_NUMBER)
        view.setEnableRightButton(newCarts.size > DISPLAY_CART_COUNT_CONDITION)

        newCarts = submitNewCarts(newCarts)
        view.setCartItemsView(newCarts)
        view.setCurrentPage(cartPage.currentPage)
    }

    private fun submitNewCarts(newCarts: List<CartProductModel>): List<CartProductModel> {
        var newCarts1 = newCarts
        val subToIndex =
            if (newCarts1.size > DISPLAY_CART_COUNT_CONDITION) newCarts1.lastIndex else newCarts1.size
        newCarts1 = newCarts1.subList(CART_LIST_FIRST_INDEX, subToIndex)
        return newCarts1
    }

    private fun getNewCarts(): List<CartProductModel> {
        val startPosition = cartPage.getStartItemPosition()
        return cartRepository.getCarts(startPosition, GET_CART_ITEM_COUNT)
    }

    override fun deleteCartItem(itemId: Long) {
        cartRepository.deleteCartByProductId(itemId)
        loadCartItems()
    }

    override fun decrementPage() {
        cartPage = cartPage.decrementPage()
        loadCartItems()
    }

    override fun incrementPage() {
        cartPage = cartPage.incrementPage()
        loadCartItems()
    }

    override fun changeAllCartSelectedStatus(isSelected: Boolean) {
        // cartRepository.changeAllCartSelectedStatus(isSelected)
    }

    companion object {
        private const val FIRST_PAGE_NUMBER = 0
        private const val DISPLAY_CART_COUNT_CONDITION = 3
        private const val CART_LIST_FIRST_INDEX = 0
        private const val GET_CART_ITEM_COUNT = 4
    }
}
