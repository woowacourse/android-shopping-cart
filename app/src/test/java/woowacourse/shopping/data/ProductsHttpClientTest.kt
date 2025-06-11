package woowacourse.shopping.data

import okhttp3.Response
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class ProductsHttpClientTest {
    private val client = ProductsHttpClient("http://localhost:12345")

    @Test
    fun `모든 상품을 가져올 수 있다`() {
        val response: Response = client.getProducts()
        assertThat(response.body?.string()).isEqualTo(
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
            """.trimIndent(),
        )
    }

    @Test
    fun `상품의 id로 상품을 가져올 수 있다`() {
        val response: Response = client.getProductById(1)
        assertThat(response.body?.string()).isEqualTo(
            """
            {
                "id": 1,
                "name": "치킨",
                "price": 10000,
                "imageUrl": "http://example.com/chicken.jpg"
            }
            """.trimIndent(),
        )
    }

    @Test
    fun `장바구니를 불러올 수 있다`() {
        val response: Response = client.getCartItems()
        assertThat(response.body?.string()).isEqualTo(
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
            """.trimIndent(),
        )
    }

    companion object {
        @BeforeAll
        @JvmStatic
        fun startMockWebServer() {
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
            mockWebServer.start(12345)
        }
    }
}
