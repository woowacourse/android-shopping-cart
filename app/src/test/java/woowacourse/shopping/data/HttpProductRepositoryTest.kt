package woowacourse.shopping.data

import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.data.repository.HttpProductRepository
import java.io.IOException
import java.util.concurrent.TimeUnit

class HttpProductRepositoryTest {
    private lateinit var server: MockWebServer
    private lateinit var repository: HttpProductRepository

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        server.start()
        repository =
            HttpProductRepository(
                baseUrl = server.url("/").toString(),
                client =
                    OkHttpClient
                        .Builder()
                        .callTimeout(10, TimeUnit.SECONDS)
                        .build(),
            )
    }

    @AfterEach
    fun shutDown() {
        server.shutdown()
    }

    @Test
    fun `상품 목록을 조회하고 offset과 limit에 맞게 반환한다`() =
        runTest {
            server.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(
                        """
                        [
                            {
                                "id": 1,
                                "name": "아메리카노",
                                "price": 4500,
                                "imageUrl": "1"
                            },
                            {
                                "id": 2,
                                "name": "카페라떼",
                                "price": 5000,
                                "imageUrl": "2"
                            },
                            {
                                "id": 3,
                                "name": "카푸치노",
                                "price": 5500,
                                "imageUrl": "3"
                            }
                        ]
                        """.trimIndent(),
                    ),
            )

            val products = repository.getProducts(offset = 1, limit = 1)

            assertThat(products).hasSize(1)
            assertThat(products[0].id).isEqualTo("2")
            assertThat(products[0].getName()).isEqualTo("카페라떼")
            assertThat(products[0].getPrice()).isEqualTo(5000)
            assertThat(products[0].imageUrl).isEqualTo("2")
            assertThat(server.takeRequest().path).isEqualTo("/products")
        }

    @Test
    fun `상품 id로 상품을 조회한다`() =
        runTest {
            server.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(
                        """
                        {
                            "id": 10,
                            "name": "아이스티",
                            "price": 6000,
                            "imageUrl": "10"
                        }
                        """.trimIndent(),
                    ),
            )

            val product = repository.getProductById("10")

            assertThat(product.id).isEqualTo("10")
            assertThat(product.getName()).isEqualTo("아이스티")
            assertThat(product.getPrice()).isEqualTo(6000)
            assertThat(product.imageUrl).isEqualTo("10")
            assertThat(server.takeRequest().path).isEqualTo("/products/10")
        }

    @Test
    fun `상품 목록 조회 응답이 실패하면 예외가 발생한다`() =
        runTest {
            server.enqueue(MockResponse().setResponseCode(500))

            assertThrows<IOException> {
                repository.getProducts(offset = 0, limit = 20)
            }
        }

    @Test
    fun `상품 상세 조회 응답이 실패하면 예외가 발생한다`() =
        runTest {
            server.enqueue(MockResponse().setResponseCode(404))

            assertThrows<IOException> {
                repository.getProductById("-1")
            }
        }
}
