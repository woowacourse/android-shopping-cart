package woowacourse.shopping.presentation.view.cart

import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.data.respository.cart.CartRepository
import woowacourse.shopping.presentation.model.CartModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private var currentPage: Int = 1
) : CartContract.Presenter {
    private val carts =
        cartRepository.getAllCarts().map {
            it.toUIModel().apply {
                product.count = it.count
                checked = it.checked == 1
            }
        }.toMutableList()

    private val startPosition: Int
        get() = (currentPage - 1) * DISPLAY_CART_COUNT_CONDITION

    override fun loadCartItems() {
        view.setEnableLeftButton(currentPage != FIRST_PAGE_NUMBER)
        view.setEnableRightButton(carts.size > startPosition + DISPLAY_CART_COUNT_CONDITION)

        val newCarts = getCurrentPageCarts()
        view.setCartItemsView(newCarts)
    }

    private fun getCurrentPageCarts(): List<CartModel> {
        return carts.subList(startPosition, getCurrentPageCartLastIndex())
    }

    private fun getCurrentPageCartLastIndex(): Int =
        if (carts.size > startPosition + DISPLAY_CART_COUNT_CONDITION)
            startPosition + DISPLAY_CART_COUNT_CONDITION
        else
            carts.size

    override fun deleteCartItem(itemId: Long) {
        cartRepository.deleteCartByCartId(itemId)
        carts.removeIf { it.id == itemId }

        view.setEnableLeftButton(currentPage != FIRST_PAGE_NUMBER)
        view.setEnableRightButton(carts.size > startPosition + DISPLAY_CART_COUNT_CONDITION)

        view.setChangedCartItemsView(carts.subList(startPosition, getCurrentPageCartLastIndex()))
    }

    override fun calculatePreviousPage() {
        view.setPageCountView(--currentPage)
    }

    override fun calculateNextPage() {
        view.setPageCountView(++currentPage)
    }

    override fun calculateTotalPrice() {
        val totalPrice = carts.sumOf {
            if (it.checked)
                (it.product.price * it.product.count)
            else
                0
        }
        view.setTotalPriceView(totalPrice)
    }

    override fun updateProductCount(cartId: Long, count: Int) {
        if (count == 0) {
            deleteCartItem(cartId)
        }
        cartRepository.updateCartCountByCartId(cartId, count)
        carts.find { it.id == cartId }?.product?.count = count
    }

    override fun updateProductChecked(cartId: Long, checked: Boolean) {
        carts.find { it.id == cartId }?.let {
            if (it.checked == checked) return@let
            it.checked = checked
            cartRepository.updateCartCheckedByCartId(it.id, it.checked)
        }
        view.setAllCartChecked(isAllChecked())
    }

    private fun isAllChecked(): Boolean {
        return getCurrentPageCarts().all { it.checked }
    }

    override fun updateCurrentPageAllProductChecked(isChecked: Boolean) {
        for (index in startPosition until getCurrentPageCartLastIndex()) {
            updateProductChecked(carts[index].id, isChecked)
        }
    }

    companion object {
        private const val FIRST_PAGE_NUMBER = 1
        private const val DISPLAY_CART_COUNT_CONDITION = 3
    }
}
