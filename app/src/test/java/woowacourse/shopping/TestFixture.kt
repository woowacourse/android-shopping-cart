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
    imageUrl: String = "https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1717977600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=jjZ9gCGElFMx8druqQBDkJzs4DH63phHkPxed4C9L3zVCoTV7XpxN58haKoLSFn3QIplsaREj2dUxlfCtym-x5edhFH078DeazrunG99WoKeYnuu2xmxDdSoJ7bckyLltypAUxYF0HQhRobKtSnIuWUQpHpu27lYSuTxsmWmmTrmg1waiPMnZHwaMgFU71Cb54OGn1SvK3Q1dasdhwsELC0wXdqb81wjFQ8fjjiYgfMJ4makKT3Ia6rAC1VF5dnRbHsL1FpGni3RrQ6nxMYjCzp7LVDaa5PCm8g9rGgEGm-AbMwXdenh7ZbZe3W2mbhfmve1V9RcHwSoXqAwD16zWQ__",
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
