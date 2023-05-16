package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.ui.productdetail.uistate.ProductDetailUIState

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ProductDetailContract.Presenter {
    override fun onLoadProduct(productId: Long) {
        val product = productRepository.findById(productId) ?: return
        view.setProduct(ProductDetailUIState.from(product))
    }

    override fun onAddProductToCart(productId: Long) {
        val product = productRepository.findById(productId) ?: return
        cartRepository.save(product)
    }
}
