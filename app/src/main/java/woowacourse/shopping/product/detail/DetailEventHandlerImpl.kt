package woowacourse.shopping.product.detail

import android.util.Log
import woowacourse.shopping.product.ProductQuantityHandler
import woowacourse.shopping.product.catalog.ProductUiModel

class DetailEventHandlerImpl(
    private val viewModel: DetailViewModel,
) : DetailEventHandler,
    ProductQuantityHandler {
    override fun onPlusQuantity(product: ProductUiModel) {
        Log.d("increaseQuantity", "클릭클릭")
        viewModel.increaseQuantity()
    }

    override fun onMinusQuantity(product: ProductUiModel) {
        Log.d("decreaseQuantity", "클릭클릭")
        viewModel.decreaseQuantity()
    }

    override fun onAddCartItem(product: ProductUiModel) {
        viewModel.addToCart(product)
    }
}
