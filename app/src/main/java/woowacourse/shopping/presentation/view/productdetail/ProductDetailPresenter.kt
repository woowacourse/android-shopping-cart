package woowacourse.shopping.presentation.view.productdetail

import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.data.respository.cart.CartRepository
import woowacourse.shopping.data.respository.product.ProductRepository
import woowacourse.shopping.data.respository.product.ProductRepositoryImpl
import woowacourse.shopping.presentation.model.RecentProductModel

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val productId: Long,
    private val productRepository: ProductRepository = ProductRepositoryImpl(),
    private val cartRepository: CartRepository
) :
    ProductDetailContract.Presenter {
    private val product = productRepository.getDataById(productId).toUIModel()

    override fun loadLastRecentProductInfo(recentProduct: RecentProductModel?) {
        if (recentProduct == null || recentProduct.id == UNABLE_ID) {
            view.setGoneOfLastRecentProductInfoView()
            return
        }
        view.setVisibleOfLastRecentProductInfoView(recentProduct)
    }

    override fun loadProductInfo() {
        if (product.id == UNABLE_ID) {
            view.handleErrorView()
            view.exitProductDetailView()
            return
        }
        view.setProductInfoView(product)
    }

    override fun addCart(count: Int) {
        cartRepository.addCart(product.id, count)
        view.addCartSuccessView()
        view.exitProductDetailView()
    }

    override fun showCount() {
        view.showCountView(product)
    }

    companion object {
        private const val UNABLE_ID = -1L
    }
}
