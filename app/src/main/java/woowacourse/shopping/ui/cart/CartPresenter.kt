package woowacourse.shopping.ui.cart

import woowacourse.shopping.repository.CartItemRepository
import woowacourse.shopping.ui.cart.uistate.CartItemUIState

class CartPresenter(
    private val view: CartContract.View,
    private val cartItemRepository: CartItemRepository,
) : CartContract.Presenter {

    private var currentPage = 0
    private val maxPage
        get() = (cartItemRepository.countAll() - 1) / PAGE_SIZE + 1

    private val cartItemSelectionStates = CartItemSelectionStates()

    override fun getCurrentPage(): Int {
        return currentPage
    }

    override fun restoreCurrentPage(currentPage: Int) {
        this.currentPage = currentPage
        showCartItemsOfCurrentPage()
        refreshPageUIState()
        refreshOrderUIState()
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

    override fun onLoadCartItemsLastPage() {
        currentPage = maxPage
        showCartItemsOfCurrentPage()
        refreshPageUIState()
    }

    override fun onDeleteCartItem(productId: Long) {
        cartItemRepository.deleteByProductId(productId)
        cartItemSelectionStates[productId] = false
        showCartItemsOfCurrentPage()
        refreshPageUIState()
        refreshOrderUIState()
    }

    private fun showCartItemsOfCurrentPage() {
        val offset = (currentPage - 1) * PAGE_SIZE
        val cartItems = cartItemRepository.findAllOrderByAddedTime(PAGE_SIZE, offset)
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

    override fun onChangeCartItemSelection(productId: Long, isSelected: Boolean) {
        cartItemSelectionStates[productId] = isSelected
        view.setCartItemSelected(productId, isSelected)
        refreshOrderUIState()
    }

    private fun refreshOrderUIState() {
        val cartItems = cartItemRepository.findAll()
        val selectedCartItems = cartItems.filter { cartItemSelectionStates[it.product.id] }
        val orderPrice =
            if (selectedCartItems.isNotEmpty()) selectedCartItems.sumOf { it.product.price * it.count } else 0
        view.setOrderPrice(orderPrice)
        view.setOrderCount(selectedCartItems.size)
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
