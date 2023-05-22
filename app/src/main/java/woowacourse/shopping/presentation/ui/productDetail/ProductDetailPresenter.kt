package woowacourse.shopping.presentation.ui.productDetail

import android.util.Log
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.domain.util.WoowaResult
import woowacourse.shopping.domain.util.WoowaResult.FAIL
import woowacourse.shopping.domain.util.WoowaResult.SUCCESS

class ProductDetailPresenter(
    view: ProductDetailContract.View,
    productId: Long,
    productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ProductDetailContract.Presenter {
    private lateinit var product: Product

    init {
        when (val woowaResult = productRepository.getProduct(productId)) {
            is SUCCESS -> product = woowaResult.data
            is FAIL -> {
                view.handleNoSuchProductError()
                Log.d("ERROR", woowaResult.error.errorMessage)
            }
        }
        fetchLastViewedProduct(view, productRepository)
        productRepository.addRecentlyViewedProduct(productId)
    }

    private fun fetchLastViewedProduct(
        view: ProductDetailContract.View,
        productRepository: ProductRepository,
    ) {
        val result: WoowaResult<RecentlyViewedProduct> = productRepository.getLastViewedProduct()
        Log.d("[ERROR]", "result: $result")
        when (result) {
            is SUCCESS -> view.setBindingData(product, result.data)
            is FAIL -> {
                view.handleNoSuchProductError()
                Log.d("[ERROR]", result.error.errorMessage)
            }
        }
    }

    override fun addProductInCart() {
        shoppingCartRepository.insertProductInCart(ProductInCart(product, 1))
    }
}
