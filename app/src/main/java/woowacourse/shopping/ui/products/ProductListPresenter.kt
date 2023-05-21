package woowacourse.shopping.ui.products

import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentlyViewedProductRepository
import woowacourse.shopping.ui.products.uistate.ProductUIState
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
    private val productRepository: ProductRepository,
) : ProductListContract.Presenter {

    override fun loadRecentlyViewedProducts() {
        view.setRecentlyViewedProducts(
            recentlyViewedProductRepository.findAll().map(RecentlyViewedProductUIState::from)
                .reversed().take(MAX_SIZE_RECENTLY_VIEWED_PRODUCTS),
        )
    }

    override fun loadProducts(limit: Int, offset: Int) {
        view.addProducts(productRepository.findAll(limit, offset).map(ProductUIState::from))
    }

    companion object {
        private const val MAX_SIZE_RECENTLY_VIEWED_PRODUCTS = 10
    }
}
