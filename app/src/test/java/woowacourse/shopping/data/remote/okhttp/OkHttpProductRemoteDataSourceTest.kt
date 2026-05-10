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
import woowacourse.shopping.data.remote.datasource.ProductRemoteDataSource
import woowacourse.shopping.data.remote.datasource.okhttp.OkHttpProductRemoteDataSource

class OkHttpProductRemoteDataSourceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var dataSource: ProductRemoteDataSource

    @BeforeEach
    fun setUp() {
        mockWebServer = startMockWebServer()

        dataSource =
            OkHttpProductRemoteDataSource(
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
    fun getProducts() =
        runTest {
            val products = dataSource.getProducts()

            assertThat(products).hasSize(2)
            assertThat(products.first().id).isEqualTo(1)
            assertThat(products.first().name).isEqualTo("치킨")
        }

    @Test
    fun getProduct() =
        runTest {
            val product = dataSource.getProduct(1)

            assertThat(product.id).isEqualTo(1)
            assertThat(product.name).isEqualTo("치킨")
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

        val products =
            """
            [
                {
                    "id": 1,
                    "name": "치킨",
                    "price": 10000,
                    "imageUrl": "http://example.com/chicken.jpg"
                },
                {
                    "id": 2,
                    "name": "피자",
                    "price": 20000,
                    "imageUrl": "http://example.com/pizza.jpg"
                }
            ]
            """.trimIndent()

        val product =
            """
            {
                "id": 1,
                "name": "치킨",
                "price": 10000,
                "imageUrl": "http://example.com/chicken.jpg"
            }
            """.trimIndent()

        val dispatcher =
            object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse =
                    when (request.path) {
                        "/products" -> {
                            MockResponse()
                                .setHeader("Content-Type", "application/json")
                                .setResponseCode(200)
                                .setBody(products)
                        }
                        "/products/1" -> {
                            MockResponse()
                                .setHeader("Content-Type", "application/json")
                                .setResponseCode(200)
                                .setBody(product)
                        }
                        else -> {
                            MockResponse().setResponseCode(404)
                        }
                    }
            }

        mockWebServer.dispatcher = dispatcher

        return mockWebServer
    }
}
