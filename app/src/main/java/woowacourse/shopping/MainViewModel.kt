package woowacourse.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _count = MutableLiveData(0)
    val count: LiveData<Int> get() = _count

    fun increment() {
        _count.postValue(_count.value?.plus(1))
    }

    fun decrement() {
        _count.value = _count.value?.minus(1)
    }
}
