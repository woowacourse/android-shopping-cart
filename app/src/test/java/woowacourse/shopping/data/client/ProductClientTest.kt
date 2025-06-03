package woowacourse.shopping.data.client

import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.dto.ProductDto
import woowacourse.shopping.util.InstantTaskExecutorExtension


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(InstantTaskExecutorExtension::class)
class ProductClientTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var client: ProductClient
    private val gson = Gson()

    @BeforeAll
    fun setUpAll() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val baseUrl = mockWebServer.url("/").toString()
        client = ProductClient(baseUrl)
    }

    @AfterAll
    fun tearDownAll() {
        mockWebServer.shutdown()
    }

    @Test
    fun `fetchProducts는 서버로부터 페이지 상품 리스트를 받아온다`() {
        val fakeProducts = (1..10).map {
            ProductDto(
                id = it,
                name = "Product $it",
                imageUrl = "http://example.com/image$it.jpg",
                price = it * 100
            )
        }

        val json = gson.toJson(fakeProducts)
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(json))

        val result = client.fetchProducts(page = 1, limit = 10)

        Assertions.assertEquals(10, result.size)
        Assertions.assertEquals("Product 1", result.first().name)
        Assertions.assertEquals("Product 10", result.last().name)
    }
}