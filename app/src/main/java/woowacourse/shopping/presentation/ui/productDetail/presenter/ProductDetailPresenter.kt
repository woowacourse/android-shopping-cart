package woowacourse.shopping.presentation.ui.productDetail.presenter

import android.util.Log
import androidx.annotation.WorkerThread
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.util.WoowaResult.FAIL
import woowacourse.shopping.domain.util.WoowaResult.SUCCESS
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val productRepository: ProductRepository,
) : ProductDetailContract.Presenter {

    override fun fetchProduct(id: Long) {
        val productFromRemote = getProductsFromRemote(id)
        view.setProduct(requireNotNull(productFromRemote).toProductInCartUiState())
//        LocalDb :
//        when (val result = productRepository.getProductFromLocal(id)) {
//            is SUCCESS -> view.setProduct(result.data.toProductInCartUiState())
//            is FAIL -> Log.d("ERROR", result.error.errorMessage)
//        }
    }

    @WorkerThread
    private fun getProductsFromRemote(id: Long): Product? {
        var result: Product? = null

        val thread = Thread {
            result = when (val state = productRepository.getProductFromRemote(id)) {
                is SUCCESS -> state.data
                is FAIL -> null
            }
        }

        thread.start()
        thread.join()

        return result
    }

    override fun fetchLastViewedProduct() {
        val result = productRepository.getLastViewedProduct()
        view.setLastViewedProduct(result)
    }

    override fun addRecentlyViewedProduct(id: Long, unit: Int) {
        Log.d("123123", id.toString())
        productRepository.addRecentlyViewedProduct(id, unit)
    }

    private fun Product.toProductInCartUiState(): ProductInCartUiState = ProductInCartUiState(
        product = this,
        quantity = 1,
        isChecked = true,
    )
}
