package woowacourse.shopping.data.datasource

import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import woowacourse.shopping.data.inventory.remote.DummyProductContent
import woowacourse.shopping.data.inventory.remote.ProductResponse
import woowacourse.shopping.data.inventory.remote.ProductsResponse
import woowacourse.shopping.data.inventory.remote.RemoteInventoryDataSourceImpl
import kotlin.test.Test

class ProductContentDataSourceTest {
    private lateinit var server: MockWebServer
    private val client = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        server.dispatcher =
            object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/0" ->
                            MockResponse().setBody(
                                json.encodeToString(
                                    ProductResponse(DummyProductContent.contents[0]),
                                ),
                            )
                        "/" ->
                            MockResponse().setBody(
                                json.encodeToString(
                                    ProductsResponse(DummyProductContent.contents),
                                ),
                            )
                        else -> MockResponse().setResponseCode(404)
                    }
                }
            }
        server.start()
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    @AfterEach
    fun teardown() {
        server.shutdown()
    }

    @Test
    fun `상품 ID로 조회된 상품을 반환한다`() {
        val dataSource = RemoteInventoryDataSourceImpl(client, server.url("/"))
        val response = dataSource.fetchProduct(0)
        assertThat(response.content.name).isEqualTo("[병천아우내] 모듬순대")
    }

    @Test
    fun `모든 상품을 반환한다`() {
        val dataSource = RemoteInventoryDataSourceImpl(client, server.url("/"))
        val response = dataSource.fetchAllProducts()
        assertThat(response.products.size).isEqualTo(30)
    }
}
