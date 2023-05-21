package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.page.Page
import woowacourse.shopping.domain.model.page.Pagination
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUi
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.ui.cart.CartContract.Presenter
import woowacourse.shopping.ui.cart.CartContract.View

class CartPresenter(
    view: View,
    private val cartRepository: CartRepository,
    cartSize: Int = 5,
) : Presenter(view) {
    private var cart: Cart = Cart(loadUnit = cartSize, minProductSize = 1)
    private var currentPage: Page = Pagination()

    private val _totalCheckSize = MutableLiveData(cartRepository.getCheckedProductCount())
    val totalCheckSize: LiveData<Int> get() = _totalCheckSize

    private val _pageCheckSize = MutableLiveData(currentPage.getCheckedProductSize(cart))
    val isAllChecked: LiveData<Boolean> = Transformations.map(_pageCheckSize) { pageCheckSize ->
        pageCheckSize == currentPage.takeItems(cart).size
    }

    override fun fetchCart(page: Int) {
        currentPage = currentPage.update(page)
        cart = cart.update(cartRepository.getProductInCartByPage(currentPage))

        view.updateNavigatorEnabled(currentPage.hasPrevious(), currentPage.hasNext(cart))
        view.updatePageNumber(currentPage.toUi())
        fetchView()
    }

    override fun changeProductCount(product: UiProduct, count: Int, increase: Boolean) {
        updateCart(changeCount(product, count, increase))
    }

    private fun changeCount(product: UiProduct, count: Int, isInc: Boolean): Cart = when (isInc) {
        true -> cart.increaseProductCount(product.toDomain(), count)
        false -> cart.decreaseProductCount(product.toDomain(), count)
    }

    override fun changeProductSelectState(product: UiProduct, isSelect: Boolean) {
        updateCart(changeSelectState(product, isSelect))
    }

    private fun changeSelectState(product: UiProduct, isSelect: Boolean): Cart =
        if (isSelect) cart.select(product.toDomain()) else cart.unselect(product.toDomain())

    override fun toggleAllCheckState() {
        updateCart(if (isAllChecked.value == true) cart.unselectAll() else cart.selectAll())
    }

    override fun removeProduct(product: UiProduct) {
        cartRepository.deleteByProductId(product.id)
        fetchCart(currentPage.value)
    }

    override fun order() {
        if (_totalCheckSize.value == 0) {
            view.showOrderFailed(); return
        }
        cartRepository.removeCheckedProducts()
        view.showOrderComplete(_totalCheckSize.value ?: 0)
    }

    override fun navigateToHome() {
        view.navigateToHome()
    }

    private fun updateCart(newCart: Cart) {
        cart = cart.update(newCart)
        cartRepository.update(currentPage.takeItems(cart))
        fetchView()
    }

    private fun fetchView() {
        _totalCheckSize.value = cartRepository.getCheckedProductCount()
        _pageCheckSize.value = currentPage.getCheckedProductSize(cart)
        view.updateTotalPrice(cartRepository.getTotalPrice())
        view.updateCart(currentPage.takeItems(cart).toUi())
    }
}
