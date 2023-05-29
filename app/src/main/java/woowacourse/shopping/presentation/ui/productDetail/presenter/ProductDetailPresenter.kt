package woowacourse.shopping.presentation.ui.productDetail.presenter

import android.util.Log
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.domain.util.WoowaResult.FAIL
import woowacourse.shopping.domain.util.WoowaResult.SUCCESS

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ProductDetailContract.Presenter {

    override fun fetchProduct(id: Long) {
        when (val result = productRepository.getProduct(id)) {
            is SUCCESS -> view.setProduct(result.data)
            is FAIL -> Log.d("ERROR", result.error.errorMessage)
        }
    }

    override fun fetchLastViewedProduct() {
        val result = productRepository.getLastViewedProduct()
        view.setLastViewedProduct(result)
    }

    override fun addRecentlyViewedProduct(id: Long, unit: Int) {
        productRepository.addRecentlyViewedProduct(id, unit)
    }

    override fun addProductInCart(product: Product) {
        shoppingCartRepository.addProductInCart(ProductInCart(product, 1, true))
    }
}
