package woowacourse.shopping.presentation.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _totalPrice = MutableLiveData<Int>()
    override val totalPrice: LiveData<Int>
        get() = _totalPrice

    override fun loadCartItems() {
        var newCarts = getNewCarts(GET_CART_ITEM_COUNT)
        view.setEnableLeftButton(cartPage.currentPage != FIRST_PAGE_NUMBER)
        view.setEnableRightButton(newCarts.size > DISPLAY_CART_COUNT_CONDITION)

        newCarts = submitNewCarts(newCarts)
        view.setCartItemsView(newCarts)
        view.setCurrentPage(cartPage.currentPage)
        _totalPrice.value = cartTotalPriceRepository.getTotalPrice()
    }

    private fun submitNewCarts(newCarts: List<CartProductModel>): List<CartProductModel> {
        var newCarts1 = newCarts
        val subToIndex =
            getCurrentPageLastCartIndex(newCarts1)
        newCarts1 = newCarts1.subList(CART_LIST_FIRST_INDEX, subToIndex)
        return newCarts1
    }

    private fun getCurrentPageLastCartIndex(newCarts1: List<CartProductModel>) =
        if (newCarts1.size > DISPLAY_CART_COUNT_CONDITION) newCarts1.lastIndex else newCarts1.size

    private fun getNewCarts(itemCount: Int): List<CartProductModel> {
        val startPosition = cartPage.getStartItemPosition()
        return cartRepository.getCarts(startPosition, itemCount)
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

    override fun changeAllCartSelectedStatus(cartsId: List<Long>, isSelected: Boolean) {
        cartRepository.updateCartsSelected(cartsId, isSelected)
        _totalPrice.value = cartTotalPriceRepository.getTotalPrice()
    }

    override fun changeCartSelectedStatus(productId: Long, isSelected: Boolean) {
        cartRepository.updateCartSelected(productId, isSelected)
        updateTotalPrice()
    }

    override fun updateProductCount(productId: Long, productCount: Int) {
        cartRepository.insertCart(productId, productCount)
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        _totalPrice.value = cartTotalPriceRepository.getTotalPrice()
    }

    companion object {
        private const val FIRST_PAGE_NUMBER = 0
        private const val DISPLAY_CART_COUNT_CONDITION = 3
        private const val CART_LIST_FIRST_INDEX = 0
        private const val GET_CART_ITEM_COUNT = 4
    }
}
