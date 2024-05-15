package woowacourse.shopping.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    private val _message: MutableLiveData<Event<MessageType>> = MutableLiveData()
    val message: LiveData<Event<MessageType>> get() = _message

    fun showMessage(messageType: MessageType) {
        _message.emit(messageType)
    }
}
