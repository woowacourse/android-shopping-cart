package woowacourse.shopping.presentation.ui.productDetail.presenter

import android.util.Log
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.domain.util.WoowaResult.FAIL
import woowacourse.shopping.domain.util.WoowaResult.SUCCESS

class ProductDetailPresenter(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ProductDetailContract.Presenter {
    lateinit var selectedProduct: Product

    override fun getProduct(id: Long) {
        when (val woowaResult = productRepository.getProduct(id)) {
            is SUCCESS -> selectedProduct = woowaResult.data
            is FAIL -> Log.d("ERROR", woowaResult.error.errorMessage)
        }
    }

    override fun addRecentlyViewedProduct(id: Long, unit: Int) {
        productRepository.addRecentlyViewedProduct(id, unit)
    }

    override fun addProductInCart() {
        shoppingCartRepository.addProductInCart(ProductInCart(selectedProduct, 1))
    }
}
