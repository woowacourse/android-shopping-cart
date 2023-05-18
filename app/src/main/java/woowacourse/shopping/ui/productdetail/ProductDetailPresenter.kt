package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.ui.productdetail.uistate.ProductDetailUIState

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ProductDetailContract.Presenter {
    override fun loadProduct(productId: Long) {
        productRepository.findById(productId)?.run {
            view.setProduct(ProductDetailUIState.from(this))
        }
    }

    override fun addProductToCart(productId: Long) {
        productRepository.findById(productId)?.run {
            cartRepository.save(CartProduct(this.id, this.imageUrl, this.name, this.price))
        } ?: view.showErrorMessage()
    }
}
