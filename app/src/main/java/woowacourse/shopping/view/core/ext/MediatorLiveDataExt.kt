package woowacourse.shopping.view.core.ext

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

fun <T> MediatorLiveData<T>.addSourceList(
    vararg liveDataArgument: MutableLiveData<*>,
    onChanged: () -> Unit,
) {
    liveDataArgument.forEach {
        this.addSource(it) { onChanged() }
    }
}
