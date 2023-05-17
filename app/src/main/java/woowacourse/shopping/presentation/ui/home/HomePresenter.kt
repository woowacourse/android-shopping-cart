package woowacourse.shopping.presentation.ui.home

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class HomePresenter(
    private val view: HomeContract.View,
    private val productRepository: ProductRepository,
) : HomeContract.Presenter {
    private val mProducts = mutableListOf<Product>()
    private val lastProductId: Long get() = mProducts.lastOrNull()?.id ?: 0

    override fun getProducts() {
        val products = productRepository.getProducts(10, lastProductId)
        val isLastProduct =
            if (products.isEmpty()) false else productRepository.isLastProduct(products.last().id)
        mProducts.addAll(products)
        view.setProducts(products, isLastProduct)
    }

    override fun getRecentlyViewed() {
        val recentlyViewed = productRepository.getRecentlyViewedProducts(30)
        if (recentlyViewed.isEmpty()) return
        view.setRecentlyViewed(recentlyViewed)
    }
}
