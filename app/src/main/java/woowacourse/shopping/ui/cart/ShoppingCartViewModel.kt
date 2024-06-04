package woowacourse.shopping.ui.cart

import androidx.lifecycle.ViewModel
import woowacourse.shopping.ui.cart.event.ShoppingCartError
import woowacourse.shopping.ui.cart.event.ShoppingCartEvent
import woowacourse.shopping.ui.util.SingleLiveData

abstract class ShoppingCartViewModel : ViewModel(), CartProductListener {
    abstract val uiState: ShoppingCartUiState

    abstract val event: SingleLiveData<ShoppingCartEvent>

    abstract val error: SingleLiveData<ShoppingCartError>

    abstract fun loadAll()

    abstract fun nextPage()

    abstract fun previousPage()

    abstract fun deleteItem(cartItemId: Long)

    abstract fun onBackClick()

    override fun onAdd(productId: Long) {
    }
}
