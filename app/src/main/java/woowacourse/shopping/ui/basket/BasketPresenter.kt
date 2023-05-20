package woowacourse.shopping.ui.basket

import woowacourse.shopping.domain.Basket
import woowacourse.shopping.domain.BasketProduct
import woowacourse.shopping.domain.Count
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.ui.mapper.toDomain
import woowacourse.shopping.ui.model.UiBasketProduct

class BasketPresenter(
    override val view: BasketContract.View,
    private val basketRepository: BasketRepository
) : BasketContract.Presenter {
    private val hasNext: Boolean get() = basket.products.lastIndex >= startId + BASKET_PAGING_SIZE
    private var currentPage: Int = 1
    private var basket: Basket = Basket(basketRepository.getAll())
    private var startId: Int = 0

    override fun addBasketProduct(product: Product) {
        val addedProduct = BasketProduct(count = Count(1), product = product)
        basketRepository.add(addedProduct)
        basket = basket.add(addedProduct)
        updateBasketProductViewData()
    }

    override fun removeBasketProduct(product: Product) {
        val removedProduct = BasketProduct(count = Count(1), product = product)
        basketRepository.minus(removedProduct)
        basket = basket.delete(removedProduct)
        updateBasketProductViewData()
    }

    override fun initBasketProducts() {
        updateBasketProductViewData()
    }

    override fun updatePreviousPage() {
        updateCurrentPage(false)
        if (startId - BASKET_PAGING_SIZE > 0) startId -= BASKET_PAGING_SIZE else startId = 0
        updateBasketProductViewData()
    }

    override fun updateNextPage() {
        updateCurrentPage(true)
        if (startId + BASKET_PAGING_SIZE < basket.products.lastIndex) startId += BASKET_PAGING_SIZE
        updateBasketProductViewData()
    }

    private fun updateBasketProductViewData() {
        updateViewBasketProduct()
        view.updateNavigatorEnabled(currentPage > 1, hasNext)
    }

    private fun updateViewBasketProduct() {
        view.updateBasketProducts(basket.getSubBasketByStartId(startId, BASKET_PAGING_SIZE))
    }

    override fun deleteBasketProduct(
        product: UiBasketProduct,
        currentProducts: List<UiBasketProduct>
    ) {
        basketRepository.remove(product.toDomain())
        basket = basket.remove(product.toDomain())
        amendStartId()
        updateBasketProductViewData()
    }

    private fun amendStartId() {
        if (basket.products.lastIndex < startId) {
            startId -= BASKET_PAGING_SIZE
            currentPage -= 1
            view.updateCurrentPage(currentPage)
        }
        if (startId < 0) startId = 0
        if (currentPage < 1) currentPage = 1
    }

    private fun updateCurrentPage(isIncrease: Boolean) {
        if (isIncrease) currentPage += 1 else currentPage -= 1
        view.updateCurrentPage(currentPage)
    }

    companion object {
        private const val BASKET_PAGING_SIZE = 5
    }
}
