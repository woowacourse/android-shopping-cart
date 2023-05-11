package woowacourse.shopping.view.productlist

import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.domain.RecentViewedRepository
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.model.toUiModel

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val productRepository: ProductRepository,
    private val recentViewedRepository: RecentViewedRepository,
) : ProductListContract.Presenter {
    private val pagination = Pagination(20, productRepository)
    private val products = mutableListOf<ProductModel>()

    override fun fetchProducts() {
        val productsUiModel = pagination.nextItems().map { it.toUiModel() }
        val viewedProducts = recentViewedRepository.findAll()
        val viewedProductsUiModel = viewedProducts.map { productRepository.find(it).toUiModel() }.reversed()
        products.addAll(productsUiModel)
        view.showProducts(viewedProductsUiModel, productsUiModel)
    }

    override fun showMoreProducts() {
    }
}
