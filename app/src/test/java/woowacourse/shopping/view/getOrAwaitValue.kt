package woowacourse.shopping.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch

fun <T> LiveData<T>.getOrAwaitValue(): T {
    var value: T? = null
    val latch = CountDownLatch(1)

    val observer =
        object : Observer<T> {
            override fun onChanged(t: T) {
                value = t
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }

    this.observeForever(observer)

    latch.await()
    return value ?: throw IllegalStateException("LiveData value was null")
}
