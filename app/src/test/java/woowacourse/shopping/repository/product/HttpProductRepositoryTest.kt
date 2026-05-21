package woowacourse.shopping.repository.product

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HttpProductRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var repository: HttpProductRepository

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        repository = HttpProductRepository(mockWebServer.url("/").toString())
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `서버로부터 상품 목록을 가져와서 도메인 모델로 변환한다`() = runBlocking {
        // given
        val productsJson = """
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

        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/products" -> {
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(productsJson)
                    }
                    else -> MockResponse().setResponseCode(404)
                }
            }
        }
        mockWebServer.dispatcher = dispatcher

        // when
        val products = repository.getProducts(0, 10)

        // then
        assertEquals(2, products.size)
        assertEquals("치킨", products[0].name)
        assertEquals(10000, products[0].price)
        assertEquals("http://example.com/chicken.jpg", products[0].imageUri)
        assertEquals("피자", products[1].name)
    }

    @Test
    fun `페이지네이션이 올바르게 동작한다`() = runBlocking {
        // given
        val productsJson = """
            [
                {"id": 1, "name": "상품1", "price": 1000, "imageUrl": "url1"},
                {"id": 2, "name": "상품2", "price": 2000, "imageUrl": "url2"},
                {"id": 3, "name": "상품3", "price": 3000, "imageUrl": "url3"}
            ]
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setHeader("Content-Type", "application/json")
                .setResponseCode(200)
                .setBody(productsJson)
        )

        // when
        val page0 = repository.getProducts(0, 2)
        
        // then
        assertEquals(2, page0.size)
        assertEquals("상품1", page0[0].name)
        assertEquals("상품2", page0[1].name)
    }
}
