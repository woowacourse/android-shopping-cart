package woowacourse.shopping.domain

import androidx.lifecycle.LiveData

fun <T> LiveData<T>.getOrAwaitValue(): T {
    var value: T? = null
    val latch = java.util.concurrent.CountDownLatch(1)

    val observer =
        object : androidx.lifecycle.Observer<T> {
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
