package woowacourse.shopping.view.productlist

import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.domain.RecentViewedRepository
import woowacourse.shopping.model.toUiModel

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val productRepository: ProductRepository,
    private val recentViewedRepository: RecentViewedRepository,
) : ProductListContract.Presenter {
    override fun fetchProducts() {
        val productsUiModel = productRepository.findAll().map { it.toUiModel() }
        val viewedProducts = recentViewedRepository.findAll()
        val viewedProductsUiModel = viewedProducts.map { productRepository.find(it).toUiModel() }.reversed()
        view.showProducts(viewedProductsUiModel, productsUiModel)
    }
}
