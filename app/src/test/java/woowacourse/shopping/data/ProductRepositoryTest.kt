package woowacourse.shopping.data

import com.example.domain.datasource.productsDatasource
import com.example.domain.model.Product
import org.junit.Test
import java.lang.Thread.sleep

internal class ProductRepositoryTest {

    private val productMockWebServer: ProductMockWebServer = ProductMockWebServer()

    @Test
    fun `처음 상품 목록을 가져온다`() {
        val productRemoteRepository = ProductRemoteMockRepositoryImpl(
            productMockWebServer,
            ProductCacheImpl.apply { clear() }
        )

        var actual = emptyList<Product>()
        productRemoteRepository.getFirstProducts(
            onSuccess = { actual = it }
        )
        sleep(1000)

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
        productRemoteRepository.getNextProducts {
            actual = it
        }
        sleep(1000)

        val expected = productsDatasource.subList(20, 40)
        assert(actual == expected)
    }
}
