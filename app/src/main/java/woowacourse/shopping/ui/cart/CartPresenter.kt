package woowacourse.shopping.ui.cart

import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.ui.cart.uistate.CartItemUIState

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
) : CartContract.Presenter {

    private var currentPage = 0
    private val maxPage
        get() = (cartRepository.findAll().size - 1) / PAGE_SIZE + 1

    override fun getCurrentPage(): Int {
        return currentPage
    }

    override fun restoreCurrentPage(currentPage: Int) {
        this.currentPage = currentPage
        showCartItemsOfCurrentPage()
        refreshPageUIState()
    }

    override fun onLoadCartItemsNextPage() {
        currentPage++
        showCartItemsOfCurrentPage()
        refreshPageUIState()
    }

    override fun onLoadCartItemsPreviousPage() {
        currentPage--
        showCartItemsOfCurrentPage()
        refreshPageUIState()
    }

    override fun onDeleteCartItem(productId: Long) {
        cartRepository.deleteById(productId)
        showCartItemsOfCurrentPage()
        refreshPageUIState()
    }

    private fun showCartItemsOfCurrentPage() {
        val offset = (currentPage - 1) * PAGE_SIZE
        val cartItems = cartRepository.findAll(PAGE_SIZE, offset)
        val cartItemUIStates = cartItems.map(CartItemUIState::from)
        view.setCartItems(cartItemUIStates)
    }

    private fun refreshPageUIState() {
        refreshStateThatCanRequestPreviousPage()
        refreshStateThatCanRequestNextPage()
        view.setPage(currentPage)
    }

    private fun refreshStateThatCanRequestPreviousPage() {
        if (currentPage <= 1) {
            view.setStateThatCanRequestPreviousPage(false)
        } else {
            view.setStateThatCanRequestPreviousPage(true)
        }
    }

    private fun refreshStateThatCanRequestNextPage() {
        if (currentPage >= maxPage) {
            view.setStateThatCanRequestNextPage(false)
        } else {
            view.setStateThatCanRequestNextPage(true)
        }
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
