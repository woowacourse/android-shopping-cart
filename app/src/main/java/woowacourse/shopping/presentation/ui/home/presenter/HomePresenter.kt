package woowacourse.shopping.presentation.ui.home.presenter

import woowacourse.shopping.domain.repository.ProductRepository

class HomePresenter(
    private val view: HomeContract.View,
    private val productRepository: ProductRepository,
) : HomeContract.Presenter {

    override fun getProducts() {
        val products = productRepository.getProducts(20, 0)
        view.setProducts(products)
    }

    override fun getRecentlyViewed() {
        val recentlyViewed = productRepository.getRecentlyViewedProducts(10)
        view.setRecentlyViewed(recentlyViewed)
    }

    override fun getMoreProducts() {
        val products = productRepository.getProducts(20, view.getProductCount())
        view.setProducts(products)
    }
}
