package woowacourse.shopping.view.productlist

import woowacourse.shopping.R
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.domain.RecentViewedRepository
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.model.toUiModel

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val productRepository: ProductRepository,
    private val recentViewedRepository: RecentViewedRepository,
) : ProductListContract.Presenter {
    private val productListPagination = ProductListPagination(PAGINATION_SIZE, productRepository)
    private val products = productListPagination.nextItems().map { it.toUiModel() }.toMutableList()

    override fun fetchProducts() {
        val viewedProducts = recentViewedRepository.findAll()
        val viewedProductsUiModel =
            viewedProducts.map { productRepository.find(it).toUiModel() }.reversed()
        view.showProducts(viewedProductsUiModel, products)
    }

    override fun showMoreProducts() {
        val viewedProducts = recentViewedRepository.findAll()
        val mark = if (viewedProducts.isNotEmpty()) products.size + 1 else products.size
        products.addAll(productListPagination.nextItems().map { it.toUiModel() })
        view.notifyAddProducts(mark, PAGINATION_SIZE)
    }

    override fun calculateSpanSize(recentViewedProducts: List<ProductModel>, position: Int): Int {
        val isHeader = recentViewedProducts.isNotEmpty() && position == 0
        val isFooter =
            if (recentViewedProducts.isNotEmpty()) position == products.size + 1 else position == products.size
        return if (isHeader || isFooter) {
            2
        } else {
            1
        }
    }

    override fun handleNextStep(itemId: Int) {
        when (itemId) {
            R.id.cart -> {
                view.handleCartMenuClicked()
            }
        }
    }

    companion object {
        private const val PAGINATION_SIZE = 20
    }
}
