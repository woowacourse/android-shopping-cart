package woowacourse.shopping.view

import androidx.lifecycle.ViewModel
import woowacourse.shopping.utils.MutableSingleLiveData
import woowacourse.shopping.utils.SingleLiveData

class MainViewModel : ViewModel() {
    private val _updateProductEvent: MutableSingleLiveData<Map<Long, Int>> = MutableSingleLiveData()
    val updateProductEvent: SingleLiveData<Map<Long, Int>> get() = _updateProductEvent

    fun saveUpdate(updateList: Map<Long, Int>) {
        _updateProductEvent.postValue(updateList)
    }
}
