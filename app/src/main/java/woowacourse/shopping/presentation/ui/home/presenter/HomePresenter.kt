package woowacourse.shopping.presentation.ui.home.presenter

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.Products
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.RecentlyViewedProducts
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.ShowMoreProducts

class HomePresenter(
    private val view: HomeContract.View,
    private val productRepository: ProductRepository,
) : HomeContract.Presenter {
    private var productsCount = 0

    override fun fetchAllProductsOnHome() {
        val recentlyViewProducts =
            productRepository.getRecentlyViewedProducts(10).toRecentProductsByView()
        val products = productRepository.getProducts(20, 0).toProductsByView()
        val showMoreButton = ShowMoreProducts

        productsCount = products.size
        view.setUpProductsOnHome(listOf(recentlyViewProducts) + products + showMoreButton)
    }

    override fun fetchMoreProducts() {
        val products = productRepository.getProducts(20, productsCount).toProductsByView()

        productsCount += products.size
        view.setUpMoreProducts(products)
    }

    private fun List<Product>.toProductsByView(): List<Products> = this.map { product ->
        Products(product)
    }

    private fun List<Product>.toRecentProductsByView(): RecentlyViewedProducts =
        RecentlyViewedProducts(this)
}
