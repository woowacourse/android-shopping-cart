package woowacourse.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object TestFixture {
    private const val TIME_OUT_MESSAGE = "LiveData value was never set."
    private const val UNCHECKED_CAST = "UNCHECKED_CAST"

    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer =
            object : Observer<T> {
                override fun onChanged(value: T) {
                    data = value
                    latch.countDown()
                    this@getOrAwaitValue.removeObserver(this)
                }
            }

        this.observeForever(observer)

        if (!latch.await(time, timeUnit)) {
            throw TimeoutException(TIME_OUT_MESSAGE)
        }

        @Suppress(UNCHECKED_CAST)
        return data as T
    }
}
