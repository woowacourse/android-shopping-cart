package woowacourse.shopping.view.productlist

import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.domain.RecentViewedRepository
import woowacourse.shopping.model.toUiModel

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val productRepository: ProductRepository,
    recentViewedRepository: RecentViewedRepository,
) : ProductListContract.Presenter {
    private val pagination = Pagination(PAGINATION_SIZE, productRepository)
    private val products = pagination.nextItems().map { it.toUiModel() }.toMutableList()
    private val viewedProducts = recentViewedRepository.findAll()
    override fun fetchProducts() {
        val viewedProductsUiModel = viewedProducts.map { productRepository.find(it).toUiModel() }.reversed()
        view.showProducts(viewedProductsUiModel, products)
    }

    override fun showMoreProducts() {
        val mark = if (viewedProducts.isNotEmpty()) products.size + 1 else products.size
        products.addAll(pagination.nextItems().map { it.toUiModel() })
        view.notifyAddProducts(mark, PAGINATION_SIZE)
    }

    companion object {
        private const val PAGINATION_SIZE = 20
    }
}
