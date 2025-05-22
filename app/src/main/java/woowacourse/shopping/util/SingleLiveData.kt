package woowacourse.shopping.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

/**
 * 출처: MVVM 피드백 강의자료
 */
abstract class SingleLiveData<T> {
    private val liveData = MutableLiveData<Event<T>>()

    protected constructor()

    protected constructor(value: T) {
        liveData.value = Event(value)
    }

    protected open fun setValue(value: T) {
        liveData.value = Event(value)
    }

    protected open fun postValue(value: T) {
        liveData.postValue(Event(value))
    }

    fun getValue() = liveData.value?.peekContent()

    fun observe(
        owner: LifecycleOwner,
        onResult: (T) -> Unit,
    ) {
        liveData.observe(owner) { it.getContentIfNotHandled()?.let(onResult) }
    }

    fun observePeek(
        owner: LifecycleOwner,
        onResult: (T) -> Unit,
    ) {
        liveData.observe(owner) { onResult(it.peekContent()) }
    }
}
