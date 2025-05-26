package woowacourse.shopping.product.catalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.repository.FakeCartProductRepositoryImpl
import woowacourse.shopping.data.repository.FakeRecentlyViewedProductRepositoryImpl
import woowacourse.shopping.data.repository.MockCatalogProductRepositoryImpl
import woowacourse.shopping.util.getOrAwaitValue

class CatalogViewModelWithMockWebServer {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var viewModel: CatalogViewModel

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher =
            object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse =
                    when (request.path) {
                        "/products" ->
                            MockResponse()
                                .setResponseCode(200)
                                .setHeader("Content-Type", "application/json")
                                .setBody(
                                    """
                                    [
                                        {
                                            "id": 1,
                                            "name": "치킨",
                                            "price": 10000,
                                            "imageUrl": "http://example.com/chicken.jpg"
                                        }
                                    ]
                                    """.trimIndent(),
                                )
                        else -> MockResponse().setResponseCode(404)
                    }
            }
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/")

        val catalogRepository = MockCatalogProductRepositoryImpl(baseUrl) // OkHttp 기반 구현체
        viewModel =
            CatalogViewModel(
                catalogProductRepository = catalogRepository,
                cartProductRepository = FakeCartProductRepositoryImpl(),
                recentlyViewedProductRepository = FakeRecentlyViewedProductRepositoryImpl(catalogRepository),
            )
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `초기 상태에서 카탈로그 상품을 받아온다`() {
        val products = viewModel.catalogItems.getOrAwaitValue()
        assertThat(products).isNotEmpty
        assertThat(products[0]).isEqualTo("치킨")
    }
}
