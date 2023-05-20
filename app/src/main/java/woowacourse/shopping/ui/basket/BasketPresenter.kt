package woowacourse.shopping.ui.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import woowacourse.shopping.domain.Basket
import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUi
import woowacourse.shopping.model.UiBasketProduct
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.ui.basket.BasketContract.Presenter
import woowacourse.shopping.ui.basket.BasketContract.View

class BasketPresenter(
    view: View,
    private val basketRepository: BasketRepository,
) : Presenter(view) {
    private var basket: Basket = Basket(loadUnit = BASKET_PAGING_SIZE, minProductSize = 1)
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

        view.updateBasket(basket.takeItemsUpToPage(currentPage).toUi())
        view.updateNavigatorEnabled(currentPage.hasPrevious(), basket.canLoadNextPage(currentPage))
        view.updatePageNumber(currentPage.toUi())
        view.updateTotalPrice(basketRepository.getTotalPrice())

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

    override fun deleteBasketProduct(basketProduct: UiBasketProduct) {
        basketRepository.deleteByProductId(basketProduct.product.id)
        fetchBasket(currentPage.value)
    }

    override fun increaseProductCount(product: UiProduct) {
        basket = basket.increaseProductCount(product.toDomain())
        basketRepository.update(basket.takeBasketUpToPage(currentPage))
        view.updateTotalPrice(basketRepository.getTotalPrice())
        _totalCheckSize.value = basketRepository.getCheckedProductCount()
    }

    override fun decreaseProductCount(product: UiProduct) {
        basket = basket.decreaseProductCount(product.toDomain())
        basketRepository.update(basket.takeBasketUpToPage(currentPage))
        view.updateTotalPrice(basketRepository.getTotalPrice())
        _totalCheckSize.value = basketRepository.getCheckedProductCount()
    }

    fun selectProduct(product: UiProduct) {
        basket = basket.select(product.toDomain())
        basketRepository.update(basket.takeBasketUpToPage(currentPage))
        view.updateTotalPrice(basketRepository.getTotalPrice())
        _totalCheckSize.value = basketRepository.getCheckedProductCount()
        _pageCheckSize.value = basket.getCheckedSize(currentPage)
        view.updateBasket(basket.takeItemsUpToPage(currentPage).toUi())
    }

    fun unselectProduct(product: UiProduct) {
        basket = basket.unselect(product.toDomain())
        basketRepository.update(basket.takeBasketUpToPage(currentPage))
        view.updateTotalPrice(basketRepository.getTotalPrice())
        _totalCheckSize.value = basketRepository.getCheckedProductCount()
        _pageCheckSize.value = basket.getCheckedSize(currentPage)
        view.updateBasket(basket.takeItemsUpToPage(currentPage).toUi())
    }

    fun toggleAllCheckState() {
        basket = if (isAllChecked.value == true) basket.unselectAll() else basket.selectAll()
        basketRepository.update(basket.takeBasketUpToPage(currentPage))

        _totalCheckSize.value = basketRepository.getCheckedProductCount()
        _pageCheckSize.value = basket.getCheckedSize(currentPage)
        view.updateBasket(basket.takeItemsUpToPage(currentPage).toUi())
        view.updateTotalPrice(basketRepository.getTotalPrice())
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

    companion object {
        private const val BASKET_PAGING_SIZE = 5
    }
}
