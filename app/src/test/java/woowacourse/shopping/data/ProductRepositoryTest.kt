package woowacourse.shopping.data

import com.example.domain.datasource.productsDatasource
import com.example.domain.model.Product
import org.junit.Test
import java.util.concurrent.CountDownLatch

internal class ProductRepositoryTest {

    private val productMockWebServer: ProductMockWebServer = ProductMockWebServer()

    @Test
    fun `처음 상품 목록을 가져온다`() {
        val productRemoteRepository = ProductRemoteMockRepositoryImpl(
            productMockWebServer,
            ProductCacheImpl.apply { clear() }
        )

        var actual = emptyList<Product>()
        val latch = CountDownLatch(1)

        Thread {
            productRemoteRepository.getFirstProducts(
                onSuccess = {
                    actual = it
                    latch.countDown()
                }
            )
        }.start()
        latch.await()

        val expected = productsDatasource.take(20)
        assert(actual == expected)
    }

    @Test
    fun `두번째 페이지 상품 목록을 가져온다`() {
        val productCache = ProductCacheImpl.apply {
            clear()
            addProducts(productsDatasource.take(20))
        }
        val productRemoteRepository = ProductRemoteMockRepositoryImpl(
            productMockWebServer,
            productCache
        )

        var actual = emptyList<Product>()
        val latch = CountDownLatch(1)

        Thread {
            productRemoteRepository.getNextProducts {
                actual = it
                latch.countDown()
            }
        }.start()
        latch.await()

        val expected = productsDatasource.subList(20, 40)
        assert(actual == expected)
    }
}
