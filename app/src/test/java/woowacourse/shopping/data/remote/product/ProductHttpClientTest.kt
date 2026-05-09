package woowacourse.shopping.data.remote.product

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import mockwebserver3.Dispatcher
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.RecordedRequest
import okhttp3.Headers
import okhttp3.OkHttpClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProductHttpClientTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var productHttpClient: ProductHttpClient

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()

        val products =
            """
            [
                {
                    "id": "00000000-0000-0000-0000-000000000001",
                    "name": "상품1",
                    "price": 10000,
                    "imageUrl": "http://example.com/product1.jpg"
                },
                {
                    "id": "00000000-0000-0000-0000-000000000002",
                    "name": "상품2",
                    "price": 20000,
                    "imageUrl": "http://example.com/product2.jpg"
                }
            ]
            """.trimIndent()
        mockWebServer.dispatcher =
            object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse =
                    when (request.target) {
                        "/products" ->
                            MockResponse(
                                code = 200,
                                headers =
                                    Headers.Companion.headersOf(
                                        "Content-Type",
                                        "application/json",
                                    ),
                                body = products,
                            )

                        else -> MockResponse(code = 404)
                    }
            }
        mockWebServer.start()
        productHttpClient =
            ProductHttpClient(
                baseUrl = mockWebServer.url("/").toString(),
                okHttpClient = OkHttpClient(),
                json = Json { ignoreUnknownKeys = true },
            )
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.close()
    }

    @Test
    fun `상품 목록을 조회할 수 있다`(): Unit =
        runBlocking {
            val actual = productHttpClient.getProducts()

            val recordedRequest = mockWebServer.takeRequest()

            assertThat(recordedRequest.target).isEqualTo("/products")
            assertThat(actual).hasSize(2)
            assertThat(actual[0].id).isEqualTo("00000000-0000-0000-0000-000000000001")
            assertThat(actual[0].name).isEqualTo("상품1")
            assertThat(actual[0].price).isEqualTo(10000)
            assertThat(actual[0].imageUrl).isEqualTo("http://example.com/product1.jpg")

            assertThat(actual[1].id).isEqualTo("00000000-0000-0000-0000-000000000002")
            assertThat(actual[1].name).isEqualTo("상품2")
            assertThat(actual[1].price).isEqualTo(20000)
            assertThat(actual[1].imageUrl).isEqualTo("http://example.com/product2.jpg")
        }
}
