package woowacourse.shopping.ui.cart

import androidx.lifecycle.ViewModel
import woowacourse.shopping.SingleLiveData
import woowacourse.shopping.ui.OnItemQuantityChangeListener
import woowacourse.shopping.ui.OnProductItemClickListener
import woowacourse.shopping.ui.cart.event.ShoppingCartError
import woowacourse.shopping.ui.cart.event.ShoppingCartEvent

abstract class ShoppingCartViewModel : ViewModel(), OnProductItemClickListener, OnItemQuantityChangeListener {
    abstract val uiState: ShoppingCartUiState

    abstract val event: SingleLiveData<ShoppingCartEvent>

    abstract val error: SingleLiveData<ShoppingCartError>

    abstract fun loadAll()

    abstract fun nextPage()

    abstract fun previousPage()

    abstract fun deleteItem(cartItemId: Long)

    abstract fun onBackClick()
}
