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
    override val view: View,
    private val basketRepository: BasketRepository,
) : Presenter {
    private var products = Products(loadUnit = BASKET_PAGING_SIZE)
    private var currentPage: PageNumber = PageNumber()

    override fun fetchBasket() {
        val currentProducts = basketRepository.getPartially(currentPage)
        products = products.copy(currentProducts)

        view.updateBasket(products.getItemsByUnit().map { it.toUi() })
        view.updateNavigatorEnabled(currentPage.hasPrevious(), products.canLoadMore())
        view.updatePageNumber(currentPage.toUi())
    }

    override fun fetchNext() {
        currentPage++
        fetchBasket()
    }

    override fun fetchPrevious() {
        currentPage--
        fetchBasket()
    }

    override fun removeBasketProduct(product: UiProduct) {
        basketRepository.remove(product.toDomain())
        fetchBasket()
    }

    override fun closeScreen() {
        view.closeScreen()
    }

    companion object {
        private const val BASKET_PAGING_SIZE = 5
    }
}
