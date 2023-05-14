package woowacourse.shopping.ui.shopping

import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.repository.DomainProductRepository
import woowacourse.shopping.domain.repository.DomainRecentProductRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUi
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct
import woowacourse.shopping.ui.shopping.ShoppingContract.Presenter
import woowacourse.shopping.ui.shopping.ShoppingContract.View
import kotlin.concurrent.thread

class ShoppingPresenter(
    override val view: View,
    private val productRepository: DomainProductRepository,
    private val recentProductRepository: DomainRecentProductRepository,
) : Presenter {
    private var products = Products()
    private var recentProducts = RecentProducts()

    override fun fetchProducts() {
        products = products.addAll(
            productRepository.getPartially(TOTAL_LOAD_PRODUCT_SIZE_AT_ONCE, products.lastId)
        )

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
        thread {
            val recentProduct = RecentProduct(product = product.toDomain())
            recentProductRepository.add(recentProduct)
            recentProducts += recentProduct
            view.updateRecentProducts(recentProducts.getItems().map { it.toUi() })
        }
        view.showProductDetail(product)
    }

    override fun fetchRecentProducts() {
        recentProducts = RecentProducts(recentProductRepository.getPartially(RECENT_PRODUCT_SIZE))
        view.updateRecentProducts(recentProducts.getItems().map { it.toUi() })
    }

    override fun inquiryRecentProductDetail(recentProduct: UiRecentProduct) {
        view.showProductDetail(recentProduct.product)
        thread { recentProductRepository.add(recentProduct.toDomain()) }
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
