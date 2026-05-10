package woowacourse.shopping

import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.remote.mock.MockWebServer
import woowacourse.shopping.data.remote.repository.WebServerRepository

class MockWebServerTest {
    @Test
    fun `상품 목록을 페이지네이션을 적용해 불러올 수 있다`() =
        runBlocking {
            // given
            val page = 0
            val pageSize = 5

            // when
            val products = repository.getProducts(page, pageSize)

            // then
            assert(products.size == 5)
            assert(products[0].id == "1")
        }

    @Test
    fun `특정 ID를 가진 상품의 상세 정보를 가져올 수 있다`() =
        runBlocking {
            // given
            val targetId = "2"

            // when
            val product = repository.getProduct(targetId)

            // then
            assert(product.id == "2")
            assert(product.name == "무엘사")
        }

    @Test
    fun `존재하지 않는 상품을 요청하면 에러가 발생한다`() =
        runBlocking {
            // given
            val invalidID = "invalidId"

            // when & then
            val result =
                runCatching {
                    repository.getProduct(invalidID)
                }
            assert(result.isFailure)
        }

    companion object {
        private lateinit var repository: WebServerRepository
        private val client = OkHttpClient()

        @JvmStatic
        @BeforeAll
        fun setUp() {
            MockWebServer.start()
            repository = WebServerRepository(client, MockWebServer.baseUrl)
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            MockWebServer.stop()
        }
    }
}
