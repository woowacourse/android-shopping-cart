package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.RecentlyViewedProduct
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentlyViewedProductRepository
import woowacourse.shopping.ui.productdetail.uistate.ProductDetailUIState
import woowacourse.shopping.ui.products.uistate.ProductUIState.Companion.MINIMUM_COUNT
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
) : ProductDetailContract.Presenter {
    override fun loadProduct(productId: Long) {
        val count = cartRepository.findById(productId)?.count

        productRepository.findById(productId)?.run {
            view.setProduct(ProductDetailUIState.from(this, count ?: MINIMUM_COUNT))
        }
    }

    override fun addProductToCart(productId: Long, count: Int) {
        val product = productRepository.findById(productId)
        if (product == null) {
            view.showErrorMessage()
            return
        }

        if (cartRepository.findById(productId) != null) {
            cartRepository.updateCount(productId, count)
        } else {
            cartRepository.save(
                CartProduct(product.id, product.imageUrl, product.name, product.price, count),
            )
        }
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

    override fun addDialogCount(count: Int) {
        view.updateCount(count + 1)
    }

    override fun minusDialogCount(count: Int) {
        if (count <= MINIMUM_COUNT) return
        view.updateCount(count - 1)
    }
}
