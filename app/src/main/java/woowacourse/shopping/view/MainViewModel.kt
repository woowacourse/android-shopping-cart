package woowacourse.shopping.view

import androidx.lifecycle.ViewModel
import woowacourse.shopping.utils.livedata.MutableSingleLiveData
import woowacourse.shopping.utils.livedata.SingleLiveData

class MainViewModel : ViewModel() {
    private val _updateProductEvent: MutableSingleLiveData<Map<Long, Int>> = MutableSingleLiveData()
    val updateProductEvent: SingleLiveData<Map<Long, Int>> get() = _updateProductEvent

    private val _updateRecentlyProductEvent: MutableSingleLiveData<Unit> = MutableSingleLiveData()
    val updateRecentlyProductEvent: SingleLiveData<Unit> get() = _updateRecentlyProductEvent

    fun saveUpdateProduct(updateList: Map<Long, Int>) {
        _updateProductEvent.setValue(updateList)
    }

    fun saveUpdateRecentlyProduct() {
        _updateRecentlyProductEvent.setValue(Unit)
    }
}
