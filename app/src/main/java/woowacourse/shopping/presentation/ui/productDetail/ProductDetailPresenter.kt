package woowacourse.shopping.presentation.ui.productDetail

import android.util.Log
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.domain.util.WoowaResult.FAIL
import woowacourse.shopping.domain.util.WoowaResult.SUCCESS

class ProductDetailPresenter(
    view: ProductDetailContract.View,
    productId: Long,
    productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ProductDetailContract.Presenter {
    lateinit var selectedProduct: Product

    init {
        when (val woowaResult = productRepository.getProduct(productId)) {
            is SUCCESS -> selectedProduct = woowaResult.data
            is FAIL -> {
                view.handleNoSuchProductError()
                Log.d("ERROR", woowaResult.error.errorMessage)
            }
        }
        productRepository.addRecentlyViewedProduct(productId)
    }

    override fun addProductInCart() {
        shoppingCartRepository.insertProductInCart(ProductInCart(selectedProduct, 1))
    }
}
