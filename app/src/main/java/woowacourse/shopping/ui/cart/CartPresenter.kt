package woowacourse.shopping.ui.cart

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.repository.CartItemRepository
import woowacourse.shopping.ui.cart.uistate.CartItemUIState
import kotlin.properties.Delegates

class CartPresenter(
    private val view: CartActivity,
    private val cartItemRepository: CartItemRepository,
) {

    private var _currentPage by Delegates.observable(0) { _, _, _ ->
        showCartItemsOfCurrentPage()
        refreshPageUIState()
        refreshAllCartItemsSelected()
    }
    private var _selectedCartItems: Set<Long> by Delegates.observable(setOf()) { _, _, _ ->
        refreshAllCartItemsSelected()
        refreshOrderUIState()
    }

    val currentPage: Int
        get() = _currentPage

    val selectedCartItems: Set<Long>
        get() = _selectedCartItems.toSet()
    private val maxPage
        get() = (cartItemRepository.countAll() - 1) / PAGE_SIZE + 1

    private fun showCartItemsOfCurrentPage() {
        val cartItems = getCartItemsOnCurrentPage()
        val cartItemUIStates =
            cartItems.map { CartItemUIState.create(it, it.product.id in _selectedCartItems) }
        view.setCartItems(cartItemUIStates)
    }

    private fun getCartItemsOnCurrentPage(): List<CartItem> {
        val offset = (_currentPage - 1) * PAGE_SIZE
        return cartItemRepository.findAllOrderByAddedTime(PAGE_SIZE, offset)
    }

    private fun refreshPageUIState() {
        refreshStateThatCanRequestPreviousPage()
        refreshStateThatCanRequestNextPage()
        view.setPage(_currentPage)
    }

    private fun refreshStateThatCanRequestPreviousPage() {
        if (_currentPage <= 1) {
            view.setStateThatCanRequestPreviousPage(false)
        } else {
            view.setStateThatCanRequestPreviousPage(true)
        }
    }

    private fun refreshStateThatCanRequestNextPage() {
        if (_currentPage >= maxPage) {
            view.setStateThatCanRequestNextPage(false)
        } else {
            view.setStateThatCanRequestNextPage(true)
        }
    }

    private fun refreshAllCartItemsSelected() {
        val cartItemsOnCurrentPage = getCartItemsOnCurrentPage()
        view.setAllCartItemsSelected(
            cartItemsOnCurrentPage.count { it.product.id in _selectedCartItems } >= cartItemsOnCurrentPage.size
                    && cartItemsOnCurrentPage.isNotEmpty()
        )
    }

    private fun refreshOrderUIState() {
        val cartItems = cartItemRepository.findAll()
        val selectedCartItems = cartItems.filter { it.product.id in _selectedCartItems }
        val orderPrice = selectedCartItems.sumOf { it.product.price * it.count }
        view.setOrderPrice(orderPrice)
        view.setOrderCount(selectedCartItems.size)
    }

    fun restoreCurrentPage(currentPage: Int) {
        this._currentPage = currentPage
    }

    fun restoreSelectedCartItems(selectedCartItems: Set<Long>) {
        this._selectedCartItems = selectedCartItems
    }

    fun onLoadCartItemsNextPage() {
        _currentPage++
    }

    fun onLoadCartItemsPreviousPage() {
        _currentPage--
    }

    fun onLoadCartItemsLastPage() {
        _currentPage = maxPage
    }

    fun onDeleteCartItem(productId: Long) {
        cartItemRepository.deleteByProductId(productId)
        _selectedCartItems = _selectedCartItems - productId
        showCartItemsOfCurrentPage()
        refreshPageUIState()
    }

    fun onChangeCartItemSelection(productId: Long, isSelected: Boolean) {
        _selectedCartItems = if (isSelected) {
            _selectedCartItems + productId
        } else {
            _selectedCartItems - productId
        }
        view.setCartItemSelected(productId, isSelected)
    }

    fun onChangeCartItemsOnCurrentPageSelection(isSelected: Boolean) {
        if (isSelected) {
            _selectedCartItems =
                _selectedCartItems + getCartItemsOnCurrentPage().map { it.product.id }
            showCartItemsOfCurrentPage()
            return
        }
        val selectedProductsIdOnCurrentPage = getCartItemsOnCurrentPage()
            .filter { it.product.id in _selectedCartItems }
            .map { it.product.id }
        if (selectedProductsIdOnCurrentPage.size >= getCartItemsOnCurrentPage().size) {
            _selectedCartItems = _selectedCartItems - selectedProductsIdOnCurrentPage.toSet()
            showCartItemsOfCurrentPage()
        }
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
