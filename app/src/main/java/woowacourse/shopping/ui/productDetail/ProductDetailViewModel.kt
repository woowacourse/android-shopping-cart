package woowacourse.shopping.ui.productDetail

import androidx.lifecycle.ViewModel
import woowacourse.shopping.ui.productDetail.event.ProductDetailError
import woowacourse.shopping.ui.productDetail.event.ProductDetailEvent
import woowacourse.shopping.ui.util.SingleLiveData

abstract class ProductDetailViewModel : ViewModel(), ProductDetailListener {
    abstract val uiState: ProductDetailUiState

    abstract val event: SingleLiveData<ProductDetailEvent>

    abstract val error: SingleLiveData<ProductDetailError>

    abstract fun loadAll()

    abstract fun addProductToCart()

    abstract fun onFinishClick()
}