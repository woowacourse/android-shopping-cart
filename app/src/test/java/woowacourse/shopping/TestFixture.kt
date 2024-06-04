package woowacourse.shopping

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import woowacourse.shopping.data.model.ProductData
import woowacourse.shopping.ui.util.Event
import woowacourse.shopping.ui.util.SingleLiveData
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun productsTestFixture(
    count: Int,
    productFixture: (Int) -> ProductData = { productTestFixture(it.toLong()) },
): List<ProductData> = List(count, productFixture)

fun productTestFixture(
    id: Long,
    name: String = "$id name",
    imageUrl: String = "1",
    price: Int = 1,
): ProductData = ProductData(id, imageUrl, name, price)

fun mockProductsTestFixture(
    count: Int,
    productFixture: (Int) -> ProductData = { mockProductTestFixture(it.toLong()) },
): List<ProductData> = List(count, productFixture)

fun mockProductTestFixture(
    id: Long,
    name: String = "$id 번째 상품 이름",
    imageUrl: String = "https://zrr.kr/aPwI",
    price: Int = id.toInt() * 100,
): ProductData = ProductData(id, imageUrl, name, price)

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

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

fun <T> SingleLiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<Event<T>> {
        override fun onChanged(value: Event<T>) {
            if (value != null) {
                data = value.getContentIfNotHandled()
                latch.countDown()
                this@getOrAwaitValue.liveData.removeObserver(this)
            }
        }
    }

    this.liveData.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        this.liveData.removeObserver(observer)
        throw TimeoutException("SingleLiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

// ObserveForever function for SingleLiveData
fun <T> SingleLiveData<T>.observeForever(observer: (T) -> Unit) {
    val liveDataObserver = Observer<Event<T>> { event ->
        event.getContentIfNotHandled()?.let(observer)
    }
    this.liveData.observeForever(liveDataObserver)
}

class InstantTaskExecutorExtension : BeforeEachCallback, AfterEachCallback {
    override fun beforeEach(context: ExtensionContext?) {
        ArchTaskExecutor.getInstance().setDelegate(
            object : TaskExecutor() {
                override fun executeOnDiskIO(runnable: Runnable) {
                    runnable.run()
                }

                override fun postToMainThread(runnable: Runnable) {
                    runnable.run()
                }

                override fun isMainThread(): Boolean {
                    return true
                }
            },
        )
    }

    override fun afterEach(context: ExtensionContext?) {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}
