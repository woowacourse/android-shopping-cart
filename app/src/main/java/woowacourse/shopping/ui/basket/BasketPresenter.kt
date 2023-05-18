package woowacourse.shopping.ui.basket

import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.ui.mapper.toDomain
import woowacourse.shopping.ui.mapper.toUi
import woowacourse.shopping.ui.model.UiBasketProduct

class BasketPresenter(
    override val view: BasketContract.View,
    private val basketRepository: BasketRepository
) : BasketContract.Presenter {
    private var hasNext: Boolean = false
    override var lastId: Int = -1
    private var currentPage: Int = 1

    override fun fetchPreviousBasketProducts(currentProducts: List<UiBasketProduct>) {
        var basketProducts = basketRepository.getPreviousPartially(
            size = BASKET_PAGING_SIZE,
            standard = currentProducts.minOfOrNull { it.id } ?: -1,
            includeStandard = false
        ).map { it.toUi() }
        hasNext = true
        lastId = basketProducts.maxOfOrNull { it.id } ?: -1
        updateBasketProductViewData(basketProducts)
    }

    override fun fetchBasketProducts(standard: Int, includeStandard: Boolean) {
        var basketProducts = basketRepository.getNextPartially(
            size = TOTAL_LOAD_BASKET_SIZE_AT_ONCE,
            standard = standard,
            includeStandard = includeStandard
        ).map { it.toUi() }
        hasNext = checkHasNext(basketProducts)
        if (hasNext) basketProducts = basketProducts.dropLast(1)
        lastId = basketProducts.maxOfOrNull { it.id } ?: -1
        updateBasketProductViewData(basketProducts)
    }

    private fun updateBasketProductViewData(basketProducts: List<UiBasketProduct>) {
        view.updateBasketProducts(basketProducts)
        view.updateNavigatorEnabled(currentPage > 1, hasNext)
    }

    override fun removeBasketProduct(
        product: UiBasketProduct,
        currentProducts: List<UiBasketProduct>
    ) {
        basketRepository.remove(product.toDomain())
        fetchBasketProducts(getStartId(product, currentProducts), true)
    }

    private fun getStartId(
        product: UiBasketProduct,
        currentProducts: List<UiBasketProduct>
    ): Int = currentProducts.toMutableList().run {
        remove(product)
        minOfOrNull { it.id } ?: -1
    }

    override fun updateCurrentPage(isIncrease: Boolean) {
        if (isIncrease) currentPage += 1 else currentPage -= 1
        view.updateCurrentPage(currentPage)
    }

    private fun checkHasNext(products: List<UiBasketProduct>): Boolean =
        products.size == TOTAL_LOAD_BASKET_SIZE_AT_ONCE

    companion object {
        private const val BASKET_PAGING_SIZE = 5
        private const val BASKET_SIZE_FOR_HAS_NEXT = 1
        private const val TOTAL_LOAD_BASKET_SIZE_AT_ONCE =
            BASKET_PAGING_SIZE + BASKET_SIZE_FOR_HAS_NEXT
    }
}
