package woowacourse.shopping

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import woowacourse.shopping.data.model.ProductData
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
