package woowacourse.shopping.ui.cart

import androidx.lifecycle.ViewModel
import woowacourse.shopping.ui.OnItemQuantityChangeListener
import woowacourse.shopping.ui.OnProductItemClickListener

abstract class ShoppingCartViewModel : ViewModel(), OnProductItemClickListener, OnItemQuantityChangeListener {
    abstract val uiState: ShoppingCartUiState
}



