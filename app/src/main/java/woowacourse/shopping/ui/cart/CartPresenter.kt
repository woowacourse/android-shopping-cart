package woowacourse.shopping.ui.cart

import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.ui.cart.uistate.CartUIState

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
) : CartContract.Presenter {

    private val checkedItems = mutableSetOf<CartUIState>()
    private var currentPage: Int = 1
    private val maxOffset: Int
        get() {
            var size = cartRepository.findAll().size
            if (size == 0) size = 1
            return (size - 1) / PAGE_SIZE + 1
        }

    override fun loadCartItemsOfCurrentPage() {
        val offset = (currentPage - 1) * PAGE_SIZE
        view.setCartItems(
            cartRepository.findAll(limit = PAGE_SIZE, offset = offset).map(CartUIState::from)
                .onEach { it.updateCheckedState(checkedItems.contains(it)) },
        )
    }

    override fun deleteCartItem(productId: Long) {
        cartRepository.deleteById(productId)
        checkedItems.removeIf { it.id == productId }
        setTotalInfo()
        view.updatePage(currentPage)
        updatePageButtons()
    }

    override fun setPageButtons() {
        view.initPageButtonClickListener()
        updatePageButtons()
    }

    override fun goLeftPage() {
        if (currentPage > 1) {
            currentPage--
            view.updatePage(currentPage)
            updatePageButtons()
        }
    }

    override fun goRightPage() {
        if (currentPage < maxOffset) {
            currentPage++
            view.updatePage(currentPage)
            updatePageButtons()
        }
    }

    private fun updatePageButtons() {
        view.updateLeftButtonsEnabled(currentPage > 1)
        view.updateRightButtonsEnabled(currentPage < maxOffset)
    }

    override fun minusItemCount(productId: Long, oldCount: Int) {
        if (oldCount > 1) {
            updateCount(productId, oldCount - 1)
        }
    }

    override fun plusItemCount(productId: Long, oldCount: Int) {
        if (oldCount < MAX_STOCK_QUANTITY) {
            updateCount(productId, oldCount + 1)
        }
    }

    private fun updateCount(productId: Long, count: Int) {
        cartRepository.updateCount(productId, count)
        checkedItems.find { it.id == productId }?.let { item ->
            val newItem = item.copy(count = count)
            checkedItems.remove(item)
            checkedItems.add(newItem)
        }
        setTotalInfo()
        view.updatePage(currentPage)
    }

    override fun updateCheckbox(isChecked: Boolean, item: CartUIState) {
        if (isChecked) {
            checkedItems.add(item)
        } else {
            checkedItems.remove(item)
        }

        setTotalInfo()
        view.updateTotalCheckbox(
            (checkedItems.containsAll(cartRepository.findAll(PAGE_SIZE, (currentPage - 1) * PAGE_SIZE).map(CartUIState::from))),
        )
    }

    override fun setTotalItemsStateAtOnce(isChecked: Boolean) {
        val offset = (currentPage - 1) * PAGE_SIZE

        cartRepository.findAll(limit = PAGE_SIZE, offset = offset).map(CartUIState::from)
            .forEach {
                if (isChecked) { checkedItems.add(it) } else { checkedItems.remove(it) }
            }

        setTotalInfo()
        view.updatePage(currentPage)
    }

    private fun setTotalInfo() {
        val totalPrice: Int = checkedItems.fold(0) { price, cartItem ->
            price + (cartItem.price * cartItem.count)
        }
        view.updateTotalPrice(totalPrice)
        view.updateTotalPurchaseButton(checkedItems.size)
    }

    companion object {
        private const val MAX_STOCK_QUANTITY = 99
        private const val PAGE_SIZE = 5
    }
}
