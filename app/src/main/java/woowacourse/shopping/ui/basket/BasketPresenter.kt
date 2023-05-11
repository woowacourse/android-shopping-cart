package woowacourse.shopping.ui.basket

import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.ui.mapper.toDomain
import woowacourse.shopping.ui.mapper.toUi
import woowacourse.shopping.ui.model.UiProduct

class BasketPresenter(
    override val view: BasketContract.View,
    private val basketRepository: BasketRepository
) : BasketContract.Presenter {

    override fun fetchBasketProducts() {
        val basketProducts = basketRepository.getPartially(BASKET_PAGING_SIZE)
        view.updateBasketProducts(basketProducts.map { it.toUi() })
    }

    override fun removeBasketProduct(product: UiProduct) {
        basketRepository.remove(product.toDomain())
        fetchBasketProducts()
    }

    companion object {
        private const val BASKET_PAGING_SIZE = 1000
    }
}
