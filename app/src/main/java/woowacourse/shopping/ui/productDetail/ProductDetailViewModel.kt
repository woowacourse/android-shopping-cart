package woowacourse.shopping.ui.productDetail

import androidx.lifecycle.ViewModel
import woowacourse.shopping.ui.util.SingleLiveData
import woowacourse.shopping.ui.OnItemQuantityChangeListener
import woowacourse.shopping.ui.OnProductItemClickListener
import woowacourse.shopping.ui.productDetail.event.ProductDetailError
import woowacourse.shopping.ui.productDetail.event.ProductDetailEvent

abstract class ProductDetailViewModel: ViewModel(), OnItemQuantityChangeListener, OnProductItemClickListener {
    abstract val uiState: ProductDetailUiState

    abstract val event: SingleLiveData<ProductDetailEvent>

    abstract val error: SingleLiveData<ProductDetailError>

    abstract fun loadAll()

    abstract fun addProductToCart()

    abstract fun onFinishClick()
}