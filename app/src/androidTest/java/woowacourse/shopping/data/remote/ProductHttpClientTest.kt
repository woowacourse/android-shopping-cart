package woowacourse.shopping.data.remote

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class ProductHttpClientTest {
    private lateinit var mockServer: MockWebServer
    private lateinit var client: ProductHttpClient

    @BeforeEach
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start()

        val url = mockServer.url("/product").toString()
        client = ProductHttpClient(url.removeSuffix("/product"))
//        client = ProductHttpClient(url)
    }

    @AfterEach
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun dataFetchedSuccessfully() {
        // given
        val mockBody =
            """
            [
                {"id": 1, "imageUrl": "https://image.msscdn.net/thumbnails/images/goods_img/20220215/2359611/2359611_17024446698621_big.jpg?w=1200", "name": "오버사이즈 칼라드 스웨트셔츠 [블랙]", "price": 35090}
            ]
            """.trimIndent()

        mockServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockBody),
        )

        // when
        val products = client.fetchProductList()

        // then
        assertAll(
            { assertThat(products.size).isEqualTo(1) },
            { assertThat(products[0].id).isEqualTo(1) },
            {
                assertThat(
                    products[0].imageUrl,
                ).isEqualTo("https://image.msscdn.net/thumbnails/images/goods_img/20220215/2359611/2359611_17024446698621_big.jpg?w=1200")
            },
            { assertThat(products[0].name).isEqualTo("오버사이즈 칼라드 스웨트셔츠 [블랙]") },
            { assertThat(products[0].price).isEqualTo(35090) },
        )
    }
}
