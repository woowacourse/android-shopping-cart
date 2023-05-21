package woowacourse.shopping.ui.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import woowacourse.shopping.domain.Basket
import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUi
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.ui.basket.BasketContract.Presenter
import woowacourse.shopping.ui.basket.BasketContract.View

class BasketPresenter(
    view: View,
    private val basketRepository: BasketRepository,
    basketSize: Int = 5,
) : Presenter(view) {
    private var basket: Basket = Basket(loadUnit = basketSize, minProductSize = 1)
    private var currentPage: PageNumber = PageNumber()

    private val _totalCheckSize = MutableLiveData(basketRepository.getCheckedProductCount())
    val totalCheckSize: LiveData<Int> get() = _totalCheckSize

    private val _pageCheckSize = MutableLiveData(basket.getCheckedSize(currentPage))
    val isAllChecked: LiveData<Boolean> = Transformations.map(_pageCheckSize) { pageCheckSize ->
        pageCheckSize == basket.takeItemsUpToPage(currentPage).size
    }

    private val _totalPrice = MutableLiveData(0)
    val totalPrice: LiveData<Int> get() = _totalPrice

    override fun fetchBasket(page: Int) {
        currentPage = currentPage.copy(page)
        basket = basket.update(basketRepository.getProductInBasketByPage(currentPage))

        view.updateBasket(basket.takeItemsUpToPage(currentPage).toUi())
        view.updateNavigatorEnabled(currentPage.hasPrevious(), basket.canLoadNextPage(currentPage))
        view.updatePageNumber(currentPage.toUi())

        _totalPrice.value = basketRepository.getTotalPrice()
        _totalCheckSize.value = basketRepository.getCheckedProductCount()
        _pageCheckSize.value = basket.getCheckedSize(currentPage)
    }

    fun loadPage(page: Int) {
        currentPage = currentPage.copy(page)
        basket = basket.update(basketRepository.getProductInBasketByPage(currentPage))

        view.updateBasket(basket.takeItemsUpToPage(currentPage).toUi())
        view.updateNavigatorEnabled(currentPage.hasPrevious(), basket.canLoadNextPage(currentPage))
        view.updatePageNumber(currentPage.toUi())

        _pageCheckSize.value = basket.getCheckedSize(currentPage)
    }

    override fun removeFromCart(product: UiProduct) {
        basketRepository.deleteByProductId(product.id)
        fetchBasket(currentPage.value)
    }

    override fun changeProductCount(product: UiProduct, count: Int, increase: Boolean) {
        if (increase) increaseProductCount(product, count) else decreaseProductCount(product, count)
        basketRepository.update(basket.takeBasketUpToPage(currentPage))
        _totalPrice.value = basketRepository.getTotalPrice()
        _totalCheckSize.value = basketRepository.getCheckedProductCount()
    }

    private fun increaseProductCount(product: UiProduct, count: Int) {
        basket = basket.increaseProductCount(product.toDomain(), count)
    }

    private fun decreaseProductCount(product: UiProduct, count: Int) {
        basket = basket.decreaseProductCount(product.toDomain(), count)
    }

    override fun changeProductSelectState(product: UiProduct, checked: Boolean) {
        if (checked) selectProduct(product) else unselectProduct(product)
        basket
        basketRepository.update(basket.takeBasketUpToPage(currentPage))
        _totalPrice.value = basketRepository.getTotalPrice()
        _totalCheckSize.value = basketRepository.getCheckedProductCount()
        _pageCheckSize.value = basket.getCheckedSize(currentPage)
        view.updateBasket(basket.takeItemsUpToPage(currentPage).toUi())
    }

    private fun selectProduct(product: UiProduct) {
        basket = basket.select(product.toDomain())
    }

    private fun unselectProduct(product: UiProduct) {
        basket = basket.unselect(product.toDomain())
    }

    fun toggleAllCheckState() {
        basket = if (isAllChecked.value == true) basket.unselectAll() else basket.selectAll()
        basketRepository.update(basket.takeBasketUpToPage(currentPage))

        _totalCheckSize.value = basketRepository.getCheckedProductCount()
        _pageCheckSize.value = basket.getCheckedSize(currentPage)
        view.updateBasket(basket.takeItemsUpToPage(currentPage).toUi())
        _totalPrice.value = basketRepository.getTotalPrice()
    }

    fun order() {
        if (_totalCheckSize.value == 0) {
            view.showOrderFailed()
            return
        }
        basketRepository.removeCheckedProducts()
        view.showOrderComplete(_totalCheckSize.value ?: 0)
        view.navigateToHome()
    }

    override fun closeScreen() {
        view.navigateToHome()
    }
}
