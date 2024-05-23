package woowacourse.shopping.presentation.shopping

import androidx.lifecycle.ViewModel
import woowacourse.shopping.presentation.common.MutableSingleLiveData
import woowacourse.shopping.presentation.common.SingleLiveData

class ShoppingEventBusViewModel : ViewModel() {
    private val _updateCartEvent = MutableSingleLiveData<Unit>()
    val updateCartEvent: SingleLiveData<Unit> = _updateCartEvent

    private val _updateProductEvent = MutableSingleLiveData<Unit>()
    val updateProductEvent: SingleLiveData<Unit> = _updateProductEvent

    fun sendUpdateCartEvent() {
        _updateCartEvent.setValue(Unit)
    }

    fun sendUpdateProductEvent() {
        _updateProductEvent.setValue(Unit)
    }
}
