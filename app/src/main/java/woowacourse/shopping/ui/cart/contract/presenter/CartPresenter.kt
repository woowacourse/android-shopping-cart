package woowacourse.shopping.ui.cart.contract.presenter

import com.example.domain.model.CartItems
import com.example.domain.repository.CartRepository
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.CartUIModel
import woowacourse.shopping.ui.cart.CartOffset
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.cart.contract.CartContract

class CartPresenter(
    val repository: CartRepository,
    val view: CartContract.View,
    offset: Int = 0,
) : CartContract.Presenter {
    private val cartItems = CartItems()
    private var cartOffset = CartOffset(offset, repository)

    override fun setUpCarts() {
        cartItems.updateItems(repository.getAll())
        view.setCarts(
            repository.getSubList(cartOffset.getOffset(), STEP).map { it.toUIModel() },
            CartUIModel(
                cartOffset.getOffset() + 5 < repository.getAll().size,
                0 < cartOffset.getOffset(),
                cartOffset.getOffset() / 5 + 1,
            ),
        )
    }

    override fun pageUp() {
        cartOffset = cartOffset.plus(STEP)
        setUpCarts()
    }

    override fun pageDown() {
        cartOffset = cartOffset.minus(STEP)
        setUpCarts()
    }

    override fun removeItem(id: Long) {
        repository.remove(id)
        if (cartOffset.getOffset() == repository.getAll().size) {
            cartOffset = cartOffset.minus(STEP)
        }
        setUpCarts()
        updateCartItems()
    }

    override fun navigateToItemDetail(id: Long) {
        val product = repository.findById(id)?.product
        product.let {
            if (it != null) {
                view.navigateToItemDetail(it.toUIModel())
            }
        }
    }

    override fun saveOffsetState(outState: MutableMap<String, Int>) {
        outState[CartActivity.KEY_OFFSET] = cartOffset.getOffset()
    }

    override fun restoreOffsetState(state: Map<String, Int>) {
        val savedOffset = state[CartActivity.KEY_OFFSET] ?: 0
        cartOffset = CartOffset(savedOffset, repository)
    }

    override fun onCheckChanged(id: Long, isChecked: Boolean) {
        val cartProduct = repository.findById(id)
        if (isChecked) {
            cartProduct?.let { cartItems.insert(it) }
        } else {
            cartProduct?.product?.id?.let {
                cartItems.remove(
                    it,
                )
            }
        }
        updateCartItems()
        setAllCheckbox()
    }

    override fun setCartItemsPrice() {
        view.setCartItemsPrice(cartItems.getPrice())
    }

    override fun onAllCheckboxClick(isChecked: Boolean) {
        repository.getSubList(cartOffset.getOffset(), STEP).map { it.toUIModel() }
            .forEach {
                onCheckChanged(it.product.id, isChecked)
                view.updateChecked(it.product.id, isChecked)
            }
    }

    override fun setAllCheckbox() {
        val pageItems = repository.getSubList(cartOffset.getOffset(), STEP).map { it.toUIModel() }
        val allChecked = pageItems.all { cartItems.isContain(it.product.id) }

        view.setAllCheckbox(allChecked)
    }

    override fun setAllOrderCount() {
        view.setAllOrderCount(cartItems.getSize())
    }

    override fun increaseCount(id: Long) {
        repository.updateCount(id, getCount(id) + 1)
        cartItems.updateItems(repository.getAll())
        view.updateItem(id, getCount(id))
        if (cartItems.isContain(id)) {
            updateCartItems()
        }
    }

    override fun decreaseCount(id: Long) {
        repository.updateCount(id, getCount(id) - 1)
        cartItems.updateItems(repository.getAll())
        view.updateItem(id, getCount(id))
        if (cartItems.isContain(id)) {
            updateCartItems()
        }
    }

    private fun getCount(id: Long): Int {
        return repository.findById(id)?.count ?: 0
    }

    private fun updateCartItems() {
        setAllOrderCount()
        setCartItemsPrice()
    }

    companion object {
        private const val STEP = 5
    }
}
