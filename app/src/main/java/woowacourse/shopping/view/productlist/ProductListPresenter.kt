package woowacourse.shopping.view.productlist

import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.domain.RecentViewedRepository
import woowacourse.shopping.model.toUiModel

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val productRepository: ProductRepository,
    private val recentViewedRepository: RecentViewedRepository
) : ProductListContract.Presenter {
    override fun fetchProducts() {
        val products = productRepository.findAll()
        view.showProducts(recentViewedRepository.findAll().map { productRepository.find(it).toUiModel() }, products.map { it.toUiModel() })
    }
}
