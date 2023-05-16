package woowacourse.shopping.ui.shopping

import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.repository.DomainProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUi
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct
import woowacourse.shopping.ui.shopping.ShoppingContract.Presenter
import woowacourse.shopping.ui.shopping.ShoppingContract.View

class ShoppingPresenter(
    view: View,
    private val productRepository: DomainProductRepository,
    private val recentProductRepository: RecentProductRepository,
) : Presenter(view) {
    private var products = Products()
    private var recentProducts = RecentProducts()

    override fun fetchAll() {
        fetchProducts()
        fetchRecentProducts()
    }

    override fun fetchProducts() {
        val newProducts = productRepository
            .getPartially(TOTAL_LOAD_PRODUCT_SIZE_AT_ONCE, products.lastId)
        products = products.addAll(newProducts)

        view.updateProducts(products.getItemsByUnit().map { it.toUi() })
        view.updateLoadMoreVisible()
    }

    private fun View.updateLoadMoreVisible() {
        if (products.canLoadMore()) {
            showLoadMoreButton()
        } else {
            hideLoadMoreButton()
        }
    }

    override fun inquiryProductDetail(product: UiProduct) {
        val recentProduct = RecentProduct(product = product.toDomain())
        recentProducts += recentProduct

        view.updateRecentProducts(recentProducts.getItems().map { it.toUi() })
        view.showProductDetail(product)

        recentProductRepository.add(recentProduct)
    }

    override fun fetchRecentProducts() {
        recentProducts = RecentProducts(recentProductRepository.getPartially(RECENT_PRODUCT_SIZE))
        view.updateRecentProducts(recentProducts.getItems().map { it.toUi() })
    }

    override fun inquiryRecentProductDetail(recentProduct: UiRecentProduct) {
        view.showProductDetail(recentProduct.product)
        recentProductRepository.add(recentProduct.toDomain())
    }

    override fun openBasket() {
        view.navigateToBasketScreen()
    }

    companion object {
        private const val RECENT_PRODUCT_SIZE = 10
        private const val LOAD_PRODUCT_SIZE_AT_ONCE = 20
        private const val PRODUCT_SIZE_FOR_HAS_NEXT = 1
        private const val TOTAL_LOAD_PRODUCT_SIZE_AT_ONCE =
            LOAD_PRODUCT_SIZE_AT_ONCE + PRODUCT_SIZE_FOR_HAS_NEXT
    }
}
