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

    private var currentPage = 1

    override fun onCreate() {
        val products = productRepository.findAll(PAGE_SIZE, 0)

        val productUIStates = products.map(ProductUIState::from)

        view.addProducts(productUIStates)
    }

    override fun onStart() {
        showRecentlyViewedProducts()
    }

    private fun showRecentlyViewedProducts() {
        val recentlyViewedProducts = recentlyViewedProductRepository.findAll()
            .reversed()
            .take(10)

        val recentlyViewedProductUIStates =
            recentlyViewedProducts.map(RecentlyViewedProductUIState::from)

        view.setRecentlyViewedProducts(recentlyViewedProductUIStates)
    }

    override fun onLoadNextPage() {
        currentPage++
        val offset = (currentPage - 1) * PAGE_SIZE
        view.addProducts(productRepository.findAll(PAGE_SIZE, offset).map(ProductUIState::from))
    }

    override fun onViewProduct(productId: Long) {
        val product = productRepository.findById(productId) ?: return

        recentlyViewedProductRepository.save(product)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
