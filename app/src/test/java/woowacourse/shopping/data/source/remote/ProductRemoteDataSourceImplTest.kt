package woowacourse.shopping.data.source.remote

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.IOException

class ProductRemoteDataSourceImplTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var dataSource: ProductRemoteDataSourceImpl
    private val client = OkHttpClient()

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        dataSource = ProductRemoteDataSourceImpl(client, mockWebServer.url("/").toString())
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `상품 목록을 성공적으로 가져오면 DTO 리스트를 반환한다`() =
        runTest {
            val jsonResponse =
                """
                [
                    {"id": "1", "name": "치킨", "price": 10000, "imageUrl": "url1"},
                    {"id": "2", "name": "피자", "price": 20000, "imageUrl": "url2"}
                ]
                """.trimIndent()

            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setHeader("Content-Type", "application/json")
                    .setBody(jsonResponse),
            )

            val result = dataSource.getProducts(0, 2)

            result.size shouldBe 2
            result[0].name shouldBe "치킨"
            result[1].price shouldBe 20000
        }

    @Test
    fun `상품 상세 정보를 성공적으로 가져오면 DTO를 반환한다`() =
        runTest {
            val jsonResponse = """{"id": "1", "name": "치킨", "price": 10000, "imageUrl": "url1"}"""

            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setHeader("Content-Type", "application/json")
                    .setBody(jsonResponse),
            )

            val result = dataSource.getProductById("1")

            result.name shouldBe "치킨"
            result.price shouldBe 10000
        }

    @Test
    fun `서버가 404 에러를 주면 예외가 발생한다`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(404))

            assertThrows<IOException> {
                dataSource.getProducts(0, 5)
            }
        }
}
