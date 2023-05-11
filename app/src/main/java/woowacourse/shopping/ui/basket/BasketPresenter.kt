package woowacourse.shopping.ui.basket

import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.ui.mapper.toDomain
import woowacourse.shopping.ui.mapper.toUi
import woowacourse.shopping.ui.model.UiProduct

class BasketPresenter(
    override val view: BasketContract.View,
    private val basketRepository: BasketRepository
) : BasketContract.Presenter {
    private var hasNext: Boolean = false
    private var lastId: Int = -1
    private val hasPrevious: Boolean
        get() = lastId > BASKET_PAGING_SIZE

    override fun fetchBasketProducts(isNext: Boolean) {
        var basketProducts = basketRepository.getPartially(
            TOTAL_LOAD_BASKET_SIZE_AT_ONCE,
            lastId,
            isNext
        ).map { it.toUi() }

        hasNext = checkHasNext(basketProducts)
        if (checkHasNext(basketProducts)) basketProducts = basketProducts.dropLast(1)
        lastId = basketProducts.maxOfOrNull { it.id } ?: -1
        view.updateBasketProducts(basketProducts)
        view.updateNavigatorEnabled(hasPrevious, hasNext)
    }

    override fun removeBasketProduct(product: UiProduct) {
        basketRepository.remove(product.toDomain())
        fetchBasketProducts()
    }

    private fun checkHasNext(products: List<UiProduct>): Boolean =
        products.size == TOTAL_LOAD_BASKET_SIZE_AT_ONCE

    companion object {
        private const val BASKET_PAGING_SIZE = 5
        private const val BASKET_SIZE_FOR_HAS_NEXT = 1
        private const val TOTAL_LOAD_BASKET_SIZE_AT_ONCE =
            BASKET_PAGING_SIZE + BASKET_SIZE_FOR_HAS_NEXT
    }
}
