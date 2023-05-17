package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.repository.CartItemRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.ui.productdetail.uistate.ProductDetailUIState
import java.time.LocalDateTime

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val productRepository: ProductRepository,
    private val cartItemRepository: CartItemRepository
) : ProductDetailContract.Presenter {
    override fun onLoadProduct(productId: Long) {
        val product = productRepository.findById(productId) ?: return
        view.setProduct(ProductDetailUIState.from(product))
    }

    override fun onAddProductToCart(productId: Long) {
        val product = productRepository.findById(productId) ?: return
        val cartItem = CartItem(product, LocalDateTime.now(), 1)
        cartItemRepository.save(cartItem)
    }
}
