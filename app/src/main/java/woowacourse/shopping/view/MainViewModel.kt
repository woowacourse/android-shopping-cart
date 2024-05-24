package woowacourse.shopping.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _updateProductEvent = MutableLiveData<Event<ProductUpdate>>()
    val updateProductEvent: LiveData<Event<ProductUpdate>> = _updateProductEvent

    fun setUpdateProductEvent(
        id: Long,
        updatedValue: Int,
    ) {
        _updateProductEvent.value = Event(ProductUpdate(id, updatedValue))
    }
}
