package woowacourse.shopping.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    private val _message: MutableLiveData<Event<MessageProvider>> = MutableLiveData()
    val message: LiveData<Event<MessageProvider>> get() = _message

    fun showMessage(messageProvider: MessageProvider) {
        _message.emit(messageProvider)
    }
}
