package woowacourse.shopping.presentation.view.productdetail

import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.data.respository.cart.CartRepository
import woowacourse.shopping.data.respository.product.ProductRepository
import woowacourse.shopping.data.respository.product.ProductRepositoryImpl

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val productRepository: ProductRepository = ProductRepositoryImpl(),
    private val cartRepository: CartRepository
) :
    ProductDetailContract.Presenter {
    override fun loadProductInfoById(id: Long) {
        val product = productRepository.getDataById(id).toUIModel()
        if (product.id == UNABLE_PRODUCT_ID) {
            view.handleErrorView()
            view.exitProductDetailView()
            return
        }
        view.setProductInfoView(product)
    }

    override fun addCart(productId: Long) {
        cartRepository.addCart(productId)
        view.addCartSuccessView()
        view.exitProductDetailView()
    }

    companion object {
        private const val UNABLE_PRODUCT_ID = -1L
    }
}
