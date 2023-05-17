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

    private var currentPage = 0
    private val offset
        get() = (currentPage - 1) * PAGE_SIZE

    override fun getCurrentPage(): Int {
        return currentPage
    }

    override fun restoreCurrentPage(currentPage: Int) {
        this.currentPage = currentPage
        val products = productRepository.findAll(offset, PAGE_SIZE)
        val productUIStates = products.map(ProductUIState::from)
        view.addProducts(productUIStates)
        refreshCanLoadMore()
    }

    override fun onLoadRecentlyViewedProducts() {
        val recentlyViewedProducts =
            recentlyViewedProductRepository.findFirst10OrderByViewedTimeDesc()

        val recentlyViewedProductUIStates =
            recentlyViewedProducts.map(RecentlyViewedProductUIState::from)
        view.setRecentlyViewedProducts(recentlyViewedProductUIStates)
    }

    override fun onLoadProductsNextPage() {
        currentPage++
        view.addProducts(productRepository.findAll(PAGE_SIZE, offset).map(ProductUIState::from))
        refreshCanLoadMore()
    }

    private fun refreshCanLoadMore() {
        val maxPage = (productRepository.countAll() - 1) / PAGE_SIZE + 1
        if (currentPage >= maxPage) view.setCanLoadMore(false)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
