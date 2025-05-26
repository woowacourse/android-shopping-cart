package woowacourse.shopping.product.catalog

import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.fixture.FakeCatalogItemRepository

@RunWith(AndroidJUnit4::class)
class CatalogItemRepositoryTest {
    private lateinit var mockWebServer: MockWebServer

    private val mockProductsJson =
        """
        [
            {
                "id": 1,
                "name": "아이스 카페 아메리카노",
                "price": 10000,
                "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg"
            },
            {
                "id": 2,
                "name": "베르가못 콜드브루",
                "price": 10000,
                "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg"
            }
        ]
        """.trimIndent()

    private val singleProductJson =
        """
        {
            "id": 1,
            "name": "아이스 카페 아메리카노",
            "price": 10000,
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg"
        }
        """.trimIndent()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher =
            object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse =
                    when (request.path) {
                        "//products" ->
                            MockResponse()
                                .setResponseCode(200)
                                .addHeader("Content-Type", "application/json")
                                .setBody(mockProductsJson)
                        "//products/1" ->
                            MockResponse()
                                .setResponseCode(200)
                                .addHeader("Content-Type", "application/json")
                                .setBody(singleProductJson)
                        else -> MockResponse().setResponseCode(404)
                    }
            }
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `상품_목록이_정상적으로_반환되는지_테스트`() {
        val baseUrl = mockWebServer.url("/").toString()
        val repository = FakeCatalogItemRepository(baseUrl)

        val result = repository.getProducts()
        assertThat(result).hasSize(2)
        assertThat(result[0].name).isEqualTo("아이스 카페 아메리카노")
    }

    @Test
    fun `상품_목록에서_일부만_조회할_수_있다`() {
        val baseUrl = mockWebServer.url("/").toString()
        val repository = FakeCatalogItemRepository(baseUrl)

        val result = repository.getSubListedProducts(0, 1)
        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo("아이스 카페 아메리카노")
    }

    @Test
    fun `전체_상품_개수를_반환한다`() {
        val baseUrl = mockWebServer.url("/").toString()
        val repository = FakeCatalogItemRepository(baseUrl)

        val size = repository.getProductsSize()
        assertThat(size).isEqualTo(2)
    }
}
