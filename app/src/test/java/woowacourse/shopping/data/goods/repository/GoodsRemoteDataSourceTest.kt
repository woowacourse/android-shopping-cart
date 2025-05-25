package woowacourse.shopping.data.goods.repository

import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.goods.GoodsDto
import woowacourse.shopping.domain.model.Goods
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class GoodsRemoteDataSourceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var dataSource: GoodsRemoteDataSourceImpl
    private val gson = Gson()

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        dataSource =
            GoodsRemoteDataSourceImpl(
                baseUrl = mockWebServer.url("/").toString().removeSuffix("/"),
                useInterceptor = false,
            )
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `상품 목록 조회 테스트`() {
        // Given
        val expectedGoods =
            listOf(
                GoodsDto(1, "상품1", 10000, "url1"),
                GoodsDto(2, "상품2", 20000, "url2"),
            )
        enqueueMockResponse(200, mapOf("products" to expectedGoods))

        // When
        val result =
            executeWithCallback<List<Goods>> { callback ->
                dataSource.fetchPageGoods(10, 0, callback)
            }

        // Then
        assertThat(result).hasSize(2)
        assertThat(result[0].name).isEqualTo("상품1")
        assertThat(result[1].name).isEqualTo("상품2")
    }

    @Test
    fun `비어있는 상품 목록 조회 테스트`() {
        // Given
        enqueueMockResponse(200, mapOf("products" to emptyList<GoodsDto>()))

        // When
        val result =
            executeWithCallback<List<Goods>> { callback ->
                dataSource.fetchPageGoods(10, 0, callback)
            }

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `상품 개수 조회 테스트`() {
        // Given
        enqueueMockResponse(200, mapOf("size" to 100))

        // When
        val result =
            executeWithCallback<Int> { callback ->
                dataSource.fetchGoodsSize(callback)
            }

        // Then
        assertThat(result).isEqualTo(100)
    }

    @Test
    fun `상품 개수 조회 실패시 반환하는 사이즈 0 반환`() {
        // Given
        enqueueMockResponse(500, "Internal Server Error")

        // When
        val result =
            executeWithCallback<Int> { callback ->
                dataSource.fetchGoodsSize(callback)
            }

        // Then
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `상품 상세 조회 테스트`() {
        // Given
        val expectedGoods = GoodsDto(1, "상품1", 10000, "url1")
        enqueueMockResponse(200, mapOf("product" to expectedGoods))

        // When
        val result =
            executeWithCallback<Goods?> { callback ->
                dataSource.fetchGoodsById(1, callback)
            }

        // Then
        assertThat(result).isNotNull
        assertThat(result!!.name).isEqualTo("상품1")
        assertThat(result.price).isEqualTo(10000)
    }

    @Test
    fun `상품 상세 조회 404시 null 반환`() {
        // Given
        enqueueMockResponse(404, mapOf("error" to "Not found"))

        // When
        val result =
            executeWithCallback<Goods?> { callback ->
                dataSource.fetchGoodsById(999, callback)
            }

        // Then
        assertThat(result).isNull()
    }

    @Test
    fun `JSON 파싱 오류시 적절한 기본값 반환`() {
        // Given - 상품 목록의 경우
        enqueueMockResponse(200, "{ invalid json }")

        // When
        val result =
            executeWithCallback<List<Goods>> { callback ->
                dataSource.fetchPageGoods(10, 0, callback)
            }

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `올바른 URL 호출 확인`() {
        // Given
        enqueueMockResponse(200, mapOf("products" to emptyList<GoodsDto>()))

        // When
        executeWithCallback<List<Goods>> { callback ->
            dataSource.fetchPageGoods(20, 10, callback)
        }

        // Then
        val request = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo("/products?limit=20&offset=10")
    }

    @Test
    fun `상품 개수 조회 URL 확인`() {
        // Given
        enqueueMockResponse(200, mapOf("size" to 50))

        // When
        executeWithCallback<Int> { callback ->
            dataSource.fetchGoodsSize(callback)
        }

        // Then
        val request = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo("/products/size")
    }

    @Test
    fun `상품 상세 조회 URL 확인`() {
        // Given
        val expectedGoods = GoodsDto(123, "상품", 5000, "url")
        enqueueMockResponse(200, mapOf("product" to expectedGoods))

        // When
        executeWithCallback<Goods?> { callback ->
            dataSource.fetchGoodsById(123, callback)
        }

        // Then
        val request = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo("/products/123")
    }

    private fun enqueueMockResponse(
        code: Int,
        body: Any,
    ) {
        val responseBody =
            when (body) {
                is String -> body
                else -> gson.toJson(body)
            }

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(responseBody),
        )
    }

    private fun <T> executeWithCallback(action: ((T) -> Unit) -> Unit): T {
        val latch = CountDownLatch(1)
        var result: T? = null

        action { value ->
            result = value
            latch.countDown()
        }

        assertThat(latch.await(5, TimeUnit.SECONDS))
            .withFailMessage("콜백이 호출되지 않았습니다")
            .isTrue()

        @Suppress("UNCHECKED_CAST")
        return result as T
    }
}
