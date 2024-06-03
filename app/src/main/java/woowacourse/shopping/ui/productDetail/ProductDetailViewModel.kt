package woowacourse.shopping.ui.productDetail

import androidx.lifecycle.ViewModel
import woowacourse.shopping.ui.OnItemQuantityChangeListener
import woowacourse.shopping.ui.OnProductItemClickListener

abstract class ProductDetailViewModel: ViewModel(), OnItemQuantityChangeListener, OnProductItemClickListener {
    abstract val uiState: ProductDetailUiState

    abstract fun loadAll()

    abstract fun addProductToCart()
}