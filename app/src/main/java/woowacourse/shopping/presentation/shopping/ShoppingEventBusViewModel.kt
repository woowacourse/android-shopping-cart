package woowacourse.shopping.presentation.shopping

import androidx.lifecycle.ViewModel
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.SingleLiveData

class ShoppingEventBusViewModel : ViewModel() {
    private val _updateCartEvent = MutableSingleLiveData<Unit>()
    val updateCartEvent: SingleLiveData<Unit> = _updateCartEvent

    private val _updateProductEvent = MutableSingleLiveData<Unit>()
    val updateRecentProductEvent: SingleLiveData<Unit> = _updateProductEvent

    fun sendUpdateCartEvent() {
        _updateCartEvent.setValue(Unit)
    }

    fun sendUpdateRecentProductEvent() {
        _updateProductEvent.setValue(Unit)
    }
}
