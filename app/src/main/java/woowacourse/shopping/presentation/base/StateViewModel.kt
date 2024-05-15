package woowacourse.shopping.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class StateViewModel : ViewModel() {
    private val _loading: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val loading: LiveData<Event<Boolean>> get() = _loading

    private val _message: MutableLiveData<Event<MessageType>> = MutableLiveData()
    val message: LiveData<Event<MessageType>> get() = _message

    fun showLoading() {
        _loading.emit(false)
    }

    fun dismissLoading() {
        _loading.emit(true)
    }

    fun showMessage(messageType: MessageType) {
        _message.emit(messageType)
    }
}
