package woowacourse.shopping.ui.cart

import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.ui.cart.uistate.CartUIState

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
) : CartContract.Presenter {

    private val checkedItems = mutableSetOf<CartUIState>()

    override fun loadCartItems(limit: Int, page: Int) {
        val offset = (page - 1) * limit
        view.setCartItems(
            cartRepository.findAll(limit = limit, offset = offset).map(CartUIState::from)
                .onEach { it.updateCheckedState(checkedItems.contains(it)) },
        )
    }

    override fun deleteCartItem(productId: Long) {
        cartRepository.deleteById(productId)
        checkedItems.removeIf { it.id == productId }
        view.updatePage()
        setCartItemPrice()
    }

    override fun setPageButtons(limit: Int) {
        var size = cartRepository.findAll().size
        if (size == 0) size = 1
        view.setPageButtonClickListener((size - 1) / limit + 1)
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
        setCartItemPrice()
        view.updatePage()
    }

    override fun updateCheckbox(isChecked: Boolean, item: CartUIState) {
        if (isChecked) {
            checkedItems.add(item)
        } else {
            checkedItems.remove(item)
        }

        setCartItemPrice()
    }

    private fun setCartItemPrice() {
        val totalPrice: Int = checkedItems.fold(0) { price, cartItem ->
            price + (cartItem.price * cartItem.count)
        }
        view.updateTotalPrice(totalPrice)
    }

    companion object {
        private const val MAX_STOCK_QUANTITY = 99
    }
}
