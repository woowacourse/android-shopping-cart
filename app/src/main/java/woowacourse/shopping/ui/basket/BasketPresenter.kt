package woowacourse.shopping.ui.basket

import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUi
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.ui.basket.BasketContract.Presenter
import woowacourse.shopping.ui.basket.BasketContract.View

class BasketPresenter(
    view: View,
    private val basketRepository: BasketRepository,
) : Presenter(view) {
    private var products: Products = Products(loadUnit = BASKET_PAGING_SIZE)
    private var currentPage: PageNumber = PageNumber()

    override fun fetchBasket(page: Int) {
        currentPage = currentPage.copy(page)

        val currentProducts = basketRepository.getPartially(currentPage)
        products = products.copy(currentProducts)

        view.updateBasket(products.getItemsByUnit().map { it.toUi() })
        view.updateNavigatorEnabled(currentPage.hasPrevious(), products.canLoadMore())
        view.updatePageNumber(currentPage.toUi())
    }

    override fun removeBasketProduct(product: UiProduct) {
        basketRepository.remove(product.toDomain())
        fetchBasket(currentPage.value)
    }

    override fun closeScreen() {
        view.closeScreen()
    }

    companion object {
        private const val BASKET_PAGING_SIZE = 5
    }
}
