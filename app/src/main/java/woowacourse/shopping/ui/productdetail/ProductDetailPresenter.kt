package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.RecentlyViewedProduct
import woowacourse.shopping.repository.CartItemRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentlyViewedProductRepository
import woowacourse.shopping.ui.productdetail.uistate.LastViewedProductUIState
import woowacourse.shopping.ui.productdetail.uistate.ProductDetailUIState
import java.time.LocalDateTime

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val productRepository: ProductRepository,
    private val cartItemRepository: CartItemRepository,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository
) : ProductDetailContract.Presenter {
    override fun onLoadProduct(productId: Long) {
        val product = productRepository.findById(productId) ?: return
        view.setProduct(
            ProductDetailUIState.create(product, cartItemRepository.existByProductId(product.id))
        )
        val recentlyViewedProduct = RecentlyViewedProduct(product, LocalDateTime.now())
        recentlyViewedProductRepository.save(recentlyViewedProduct)
    }

    override fun onAddProductToCart(productId: Long, count: Int) {
        require(count > 0) { "장바구니에 상품을 추가하려면 개수는 1개 이상이어야 합니다." }
        val product = productRepository.findById(productId) ?: return
        val cartItem = CartItem(product, LocalDateTime.now(), count)
        cartItemRepository.save(cartItem)
    }

    override fun onLoadLastViewedProduct() {
        val recentlyViewedProducts =
            recentlyViewedProductRepository.findFirst10OrderByViewedTimeDesc()

        if (recentlyViewedProducts.size < 2) {
            view.setLastViewedProduct(null)
            return
        }

        val productDetailUIState = LastViewedProductUIState.from(recentlyViewedProducts[1])
        view.setLastViewedProduct(productDetailUIState)
    }
}
