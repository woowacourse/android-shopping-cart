package woowacourse.shopping.data.remote.okhttp

import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.remote.datasource.CartRemoteDataSource
import woowacourse.shopping.data.remote.datasource.okhttp.OkHttpCartRemoteDataSource

class OkHttpCartRemoteDataSourceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var dataSource: CartRemoteDataSource

    @BeforeEach
    fun setUp() {
        mockWebServer = startMockWebServer()

        dataSource =
            OkHttpCartRemoteDataSource(
                client = OkHttpClient(),
                baseUrl = mockWebServer.url("/").toString(),
                json = Json { ignoreUnknownKeys = true },
            )
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getCartItems() =
        runTest {
            val cartItems = dataSource.getCartItems()

            assertThat(cartItems).hasSize(2)
            assertThat(cartItems.first().id).isEqualTo(1)
            assertThat(cartItems.first().quantity).isEqualTo(5)
        }

    @Test
    fun returns404WhenRequestingInvalidPath() =
        runTest {
            val request =
                Request
                    .Builder()
                    .url(mockWebServer.url("/invalid"))
                    .build()

            val response =
                OkHttpClient()
                    .newCall(request)
                    .execute()

            assertThat(response.code).isEqualTo(404)
        }

    private fun startMockWebServer(): MockWebServer {
        val mockWebServer = MockWebServer()

        val cartItems =
            """
            [
                {
                    "id": 1,
                    "quantity": 5,
                    "product": {
                        "id": 1,
                        "price": 10000,
                        "name": "치킨",
                        "imageUrl": "http://example.com/chicken.jpg"
                    }
                },
                {
                    "id": 2,
                    "quantity": 1,
                    "product": {
                        "id": 2,
                        "price": 20000,
                        "name": "피자",
                        "imageUrl": "http://example.com/pizza.jpg"
                    }
                }
            ]
            """.trimIndent()

        val dispatcher =
            object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse =
                    when (request.path) {
                        "/cart-items" -> {
                            MockResponse()
                                .setHeader("Content-Type", "application/json")
                                .setResponseCode(200)
                                .setBody(cartItems)
                        }
                        else -> {
                            MockResponse().setResponseCode(404)
                        }
                    }
            }

        mockWebServer.dispatcher = dispatcher
        mockWebServer.start()

        return mockWebServer
    }
}
