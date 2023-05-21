package woowacourse.shopping.presentation.view.productdetail

import woowacourse.shopping.data.respository.cart.CartRepository
import woowacourse.shopping.data.respository.product.ProductRepository

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) :
    ProductDetailContract.Presenter {
    override fun loadProductInfoById(id: Long) {
        val product = productRepository.getDataById(id)
        if (product.id == -1L) {
            view.handleErrorView()
            return
        }
        view.showProductInfoView(product)
    }

    override fun loadRecentProductById(id: Long) {
        val recentProduct = productRepository.getDataById(id)
        view.showRecentProductById(recentProduct)
    }

    override fun addCart(productId: Long, count: Int) {
        val productCount = productRepository.getProductCount(productId)
        cartRepository.insertCart(productId, productCount + count)
        view.addCartSuccessView()
    }
}
