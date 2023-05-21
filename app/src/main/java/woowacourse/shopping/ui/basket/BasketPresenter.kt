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

    override fun fetchBasket(page: Int) {
        currentPage = currentPage.copy(page)
        basket = basket.update(basketRepository.getProductInBasketByPage(currentPage))

        view.updateNavigatorEnabled(currentPage.hasPrevious(), basket.canLoadNextPage(currentPage))
        view.updatePageNumber(currentPage.toUi())
        fetchView()
    }

    override fun changeProductCount(product: UiProduct, count: Int, increase: Boolean) {
        updateBasket(changeCount(product, count, increase))
    }

    private fun changeCount(product: UiProduct, count: Int, isInc: Boolean): Basket = when (isInc) {
        true -> basket.increaseProductCount(product.toDomain(), count)
        false -> basket.decreaseProductCount(product.toDomain(), count)
    }

    override fun changeProductSelectState(product: UiProduct, isSelect: Boolean) {
        updateBasket(changeSelectState(product, isSelect))
    }

    private fun changeSelectState(product: UiProduct, isSelect: Boolean): Basket =
        if (isSelect) basket.select(product.toDomain()) else basket.unselect(product.toDomain())

    override fun toggleAllCheckState() {
        updateBasket(if (isAllChecked.value == true) basket.unselectAll() else basket.selectAll())
    }

    override fun removeProduct(product: UiProduct) {
        basketRepository.deleteByProductId(product.id)
        fetchBasket(currentPage.value)
    }

    override fun order() {
        if (_totalCheckSize.value == 0) {
            view.showOrderFailed(); return
        }
        basketRepository.removeCheckedProducts()
        view.showOrderComplete(_totalCheckSize.value ?: 0)
    }

    override fun navigateToHome() {
        view.navigateToHome()
    }

    private fun updateBasket(newBasket: Basket) {
        basket = basket.update(newBasket)
        basketRepository.update(basket.takeBasketUpToPage(currentPage))
        fetchView()
    }

    private fun fetchView() {
        _totalCheckSize.value = basketRepository.getCheckedProductCount()
        _pageCheckSize.value = basket.getCheckedSize(currentPage)
        view.updateTotalPrice(basketRepository.getTotalPrice())
        view.updateBasket(basket.takeItemsUpToPage(currentPage).toUi())
    }
}
