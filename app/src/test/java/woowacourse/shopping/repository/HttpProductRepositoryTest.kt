@file:Suppress("NonAsciiCharacters")

package woowacourse.shopping.repository

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.repository.http.HttpProductRepository
import woowacourse.shopping.repository.http.ProductParsingException
import woowacourse.shopping.repository.http.ProductResponseException

class HttpProductRepositoryTest {
    @Test
    fun `상품 목록 API 응답을 도메인 객체로 변환한다`() =
        runBlocking {
            val repository =
                HttpProductRepository(
                    client = clientWithResponse(pathToBody = mapOf("/products" to productsJson)),
                    baseUrl = "https://example.com/",
                )

            val actual = repository.getProducts(fromIndex = 0, limit = 20).toList()

            assertEquals(2, actual.size)
            assertEquals(ProductId.fromRemoteId(1), actual.first().id)
            assertEquals("치킨", actual.first().name)
        }

    @Test
    fun `상품 상세 API 응답을 기준으로 ID 목록을 조회한다`() =
        runBlocking {
            val repository =
                HttpProductRepository(
                    client = clientWithResponse(pathToBody = mapOf("/products/1" to productJson)),
                    baseUrl = "https://example.com/",
                )

            val actual = repository.findAllByIds(setOf(ProductId.fromRemoteId(1)))

            assertEquals(setOf(ProductId.fromRemoteId(1)), actual.keys)
            assertEquals("치킨", actual[ProductId.fromRemoteId(1)]?.name)
        }

    @Test
    fun `서버 오류 응답이 오면 예외를 던진다`() {
        val repository =
            HttpProductRepository(
                client =
                    clientWithInterceptor {
                        Response
                            .Builder()
                            .request(it.request())
                            .protocol(Protocol.HTTP_1_1)
                            .code(500)
                            .message("Server Error")
                            .body("{}".toResponseBody())
                            .build()
                    },
                baseUrl = "https://example.com/",
            )

        assertThrows<ProductResponseException> {
            runBlocking { repository.getProducts(fromIndex = 0, limit = 20) }
        }
    }

    @Test
    fun `잘못된 JSON 응답이 오면 파싱 예외를 던진다`() {
        val repository =
            HttpProductRepository(
                client = clientWithResponse(pathToBody = mapOf("/products" to """{ "id": 1 }""")),
                baseUrl = "https://example.com/",
            )

        assertThrows<ProductParsingException> {
            runBlocking { repository.getProducts(fromIndex = 0, limit = 20) }
        }
    }

    private fun clientWithResponse(pathToBody: Map<String, String>): OkHttpClient =
        clientWithInterceptor { chain ->
            val path = chain.request().url.encodedPath
            val body = pathToBody[path] ?: "{}"
            val code = if (pathToBody.containsKey(path)) 200 else 404

            Response
                .Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .code(code)
                .message(if (code == 200) "OK" else "Not Found")
                .body(body.toResponseBody())
                .build()
        }

    private fun clientWithInterceptor(
        block: (Interceptor.Chain) -> Response,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(block)
            .build()

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
