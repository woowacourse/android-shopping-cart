package woowacourse.shopping.presentation.productdetail

import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.model.Product
import woowacourse.shopping.presentation.mapper.toPresentation

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val cartRepository: CartRepository,
) : ProductDetailContract.Presenter {

    private lateinit var currentProduct: Product

    override fun loadProductDetail(productId: Long) {
        val product = cartRepository.findProductById(productId)
        currentProduct = product
        view.showProductDetail(currentProduct.toPresentation())
    }

    override fun loadRecentProduct(productId: Long) {
        val recentProduct = cartRepository.findProductById(productId)
        view.showRecentProduct(recentProduct.toPresentation())
    }

    override fun putProductInCart(count: Int) {
        cartRepository.insertCartProduct(currentProduct.id, count)
    }
}
