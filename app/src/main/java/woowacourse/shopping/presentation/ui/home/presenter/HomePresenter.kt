package woowacourse.shopping.presentation.ui.home.presenter

import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.product.recentlyViewed.RecentlyViewedDao
import woowacourse.shopping.domain.repository.ProductRepository

class HomePresenter(
    private val view: HomeContract.View,
) : HomeContract.Presenter {
    private val productRepository: ProductRepository by lazy {
        ProductRepositoryImpl(
            productDataSource = ProductDao(),
            recentlyViewedDataSource = RecentlyViewedDao(),
        )
    }

    override fun fetchProducts() {
        val products = productRepository.getProducts(20, 0)
        view.setUpProducts(products)
    }

    override fun fetchRecentlyViewed() {
        val recentlyViewed = productRepository.getRecentlyViewedProducts(10)
        view.setUpRecentlyViewed(recentlyViewed)
    }

    override fun fetchMoreProducts(productId: Long) {
        val products = productRepository.getProducts(20, view.getProductCount())
        view.setUpProducts(products)
    }
}
