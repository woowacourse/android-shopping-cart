package woowacourse.shopping.domain

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.remote.mock.ProductWebServer
import woowacourse.shopping.data.remote.repository.ProductRepository
import woowacourse.shopping.data.remote.repository.ProductRepositoryImpl

class ProductWebServerTest {
    @Test
    fun `상품 목록을 페이지네이션을 적용해 불러올 수 있다`() = runBlocking {
        val page = 0
        val pageSize = 5

        // when
        val products = repository.getProducts(page, pageSize)

        // then
        assertEquals(5, products.size)
        assertEquals("1", products[0].id)
    }

    @Test
    fun `특정 ID를 가진 상품의 상세 정보를 가져올 수 있다`() = runBlocking {
        // given
        val targetId = "2"

        // when
        val product = repository.getProduct(targetId)

        // then
        assertEquals("2", product.id)
        assertEquals("무엘사", product.name)
    }

    @Test
    fun `존재하지 않는 상품을 요청하면 에러가 발생한다`() = runBlocking {
        // given
        val invalidID = "invalidId"

        // when & then
        val result =
            runCatching {
                repository.getProduct(invalidID)
            }

        assertEquals(true, result.isFailure)
    }

    companion object {
        private lateinit var repository: ProductRepository
        private val client = OkHttpClient()

        @JvmStatic
        @AfterAll
        fun tearDown(): Unit {
            ProductWebServer.stop()
        }

        @JvmStatic
        @BeforeAll
        fun setUp(): Unit = runBlocking {
            ProductWebServer.start()

            ProductWebServer.isReady.first { it == true }

            repository = ProductRepositoryImpl(client, ProductWebServer.baseUrl)
        }
    }

}
