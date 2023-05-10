package woowacourse.shopping.ui.products

import woowacourse.shopping.repository.ProductRepository

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val productRepository: ProductRepository,
) : ProductListContract.Presenter {
    override fun loadProducts() {
        view.setProducts(productRepository.findAll().map(ProductUIState::from))
    }
}
