@file:Suppress("NonAsciiCharacters")

package woowacourse.shopping.repository

import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.repository.http.HttpProductRepository
import woowacourse.shopping.repository.http.ProductParsingException
import woowacourse.shopping.repository.http.ProductResponseException

class HttpProductRepositoryTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var repository: HttpProductRepository

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        repository =
            HttpProductRepository(
                client = OkHttpClient(),
                baseUrl = mockWebServer.url("/").toString(),
            )
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `상품 목록 API 성공 응답을 도메인 객체로 변환한다`() =
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setHeader("Content-Type", "application/json")
                    .setBody(productsJson),
            )

            val actual = repository.getProducts(fromIndex = 0, limit = 20).toList()
            val request = mockWebServer.takeRequest()

            assertTrue(request.path?.endsWith("/products") == true)
            assertEquals(2, actual.size)
            assertEquals(ProductId.fromRemoteId(1), actual.first().id)
            assertEquals("치킨", actual.first().name)
        }

    @Test
    fun `상품 목록을 한 번 조회한 뒤에는 캐시된 목록으로 슬라이스를 반환한다`() =
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setHeader("Content-Type", "application/json")
                    .setBody(productsJson),
            )

            repository.getProducts(fromIndex = 0, limit = 20)
            val actual = repository.getProducts(fromIndex = 1, limit = 1).toList()

            assertEquals(1, mockWebServer.requestCount)
            assertEquals(1, actual.size)
            assertEquals(ProductId.fromRemoteId(2), actual.first().id)
            assertEquals("피자", actual.first().name)
        }

    @Test
    fun `상품 상세 API 성공 응답을 기준으로 ID 목록을 조회한다`() =
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setHeader("Content-Type", "application/json")
                    .setBody(productJson),
            )

            val actual = repository.findAllByIds(setOf(ProductId.fromRemoteId(1)))
            val request = mockWebServer.takeRequest()

            assertTrue(request.path?.endsWith("/products/1") == true)
            assertEquals(setOf(ProductId.fromRemoteId(1)), actual.keys)
            assertEquals("치킨", actual[ProductId.fromRemoteId(1)]?.name)
        }

    @Test
    fun `상품 목록 API가 서버 오류를 반환하면 예외를 던진다`() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setHeader("Content-Type", "application/json")
                .setBody("""{"message":"server error"}"""),
        )

        val actual =
            assertThrows<ProductResponseException> {
                runBlocking { repository.getProducts(fromIndex = 0, limit = 20) }
            }

        assertEquals(500, actual.code)
    }

    @Test
    fun `상품 상세 API가 서버 오류를 반환하면 예외를 던진다`() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setHeader("Content-Type", "application/json")
                .setBody("""{"message":"server error"}"""),
        )

        val actual =
            assertThrows<ProductResponseException> {
                runBlocking { repository.findAllByIds(setOf(ProductId.fromRemoteId(1))) }
            }

        assertEquals(500, actual.code)
    }

    @Test
    fun `상품 목록 API가 빈 응답 본문을 반환하면 파싱 예외를 던진다`() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(""),
        )

        val actual =
            assertThrows<ProductParsingException> {
                runBlocking { repository.getProducts(fromIndex = 0, limit = 20) }
            }

        assertTrue(actual.message?.contains("응답") == true)
    }

    @Test
    fun `상품 상세 API가 빈 응답 본문을 반환하면 파싱 예외를 던진다`() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(""),
        )

        val actual =
            assertThrows<ProductParsingException> {
                runBlocking { repository.findAllByIds(setOf(ProductId.fromRemoteId(1))) }
            }

        assertTrue(actual.message?.contains("응답") == true)
    }

    companion object {
        private val productsJson =
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

        private val productJson =
            """
            {
              "id": 1,
              "name": "치킨",
              "price": 10000,
              "imageUrl": "http://example.com/chicken.jpg"
            }
            """.trimIndent()
    }
}
