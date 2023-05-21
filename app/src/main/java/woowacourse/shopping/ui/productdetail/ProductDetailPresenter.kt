package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.RecentlyViewedProduct
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentlyViewedProductRepository
import woowacourse.shopping.ui.productdetail.uistate.ProductDetailUIState
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
) : ProductDetailContract.Presenter {
    override fun loadProduct(productId: Long) {
        productRepository.findById(productId)?.run {
            view.setProduct(ProductDetailUIState.from(this))
        }
    }

    override fun addProductToCart(productId: Long, count: Int) {
        productRepository.findById(productId)?.run {
            cartRepository.save(CartProduct(this.id, this.imageUrl, this.name, this.price, count))
        } ?: view.showErrorMessage()
    }

    override fun addRecentlyViewedProduct(productId: Long) {
        productRepository.findById(productId)?.run {
            recentlyViewedProductRepository.save(
                RecentlyViewedProduct(this.id, this.imageUrl, this.name, this.price),
            )
        }
    }

    override fun showLastlyViewedProduct(productId: Long) {
        recentlyViewedProductRepository.findLast()?.run {
            if (this.id != productId) {
                view.showLastlyViewedProduct(RecentlyViewedProductUIState.from(this))
            } else {
                view.hideLastlyViewedProduct()
            }
        } ?: view.hideLastlyViewedProduct()
    }
}
