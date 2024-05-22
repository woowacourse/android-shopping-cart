package woowacourse.shopping.data.network.service

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.IOException
import woowacourse.shopping.data.network.model.ProductPageResponse
import woowacourse.shopping.data.network.model.ProductResponse
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

class DefaultShoppingProductService(
    private val executor: ExecutorService,
    private val products: List<ProductResponse> = STUB_PRODUCTS,
) :
    ShoppingProductService {
    private val json: Json = Json { ignoreUnknownKeys = true }
    private val server: MockWebServer = MockWebServer()

    init {
        server.start()
    }

    override fun fetchProducts(currentPage: Int, size: Int): Future<ProductPageResponse> {
        return executor.submit(Callable {
            pushProducts(currentPage, size)
            val url = server.url("").toString()
            val connection = URL(url).openConnection() as HttpURLConnection
            try {
                connection.doInput = true
                val responseCode = connection.responseCode
                connection.connect()
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val response = inputStream.bufferedReader().use { it.readText() }
                    return@Callable response.toProductPageResponse()
                }
                error("로드 실패 $responseCode")
            } catch (e: IOException) {
                error("NetWorkError: 로드 실패  ${e.stackTraceToString()}")
            } finally {
                connection.disconnect()
            }
        })
    }

    override fun fetchProductById(id: Long): Future<ProductResponse> {
        return executor.submit(Callable {
            val product = products.find { it.id == id } ?: error("상품이 존재하지 않습니다.")
            pushProduct(product)
            val url = server.url("").toString()
            val connection = URL(url).openConnection() as HttpURLConnection
            try {
                connection.doInput = true
                val responseCode = connection.responseCode
                connection.connect()
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val response = inputStream.bufferedReader().use { it.readText() }
                    return@Callable response.toProductResponse()
                }
                error("상품 로드 실패 $responseCode")
            } catch (e: IOException) {
                error("NetWorkError : 로드 실패 ${e.stackTraceToString()}")
            } finally {
                connection.disconnect()
            }
        })
    }

    private fun pushProducts(currentPage: Int, size: Int) {
        val startIdx = (currentPage - 1) * size
        val products = products.subList(startIdx, startIdx + size)
        val pageResponse = ProductPageResponse(
            pageNumber = currentPage,
            content = products,
            totalPages = products.size / size,
            pageSize = size,
            totalElements = products.size,
            hasNext = products.size > size
        )
        val fakeResponse = MockResponse().setHeader("Content-Type", "application/json")
            .setBody(pageResponse.toEncodedString()).setResponseCode(HttpURLConnection.HTTP_OK)
        server.enqueue(fakeResponse)
    }

    private fun pushProduct(product: ProductResponse) {
        val fakeResponse = MockResponse().setHeader("Content-Type", "application/json")
            .setBody(product.toEncodedString()).setResponseCode(HttpURLConnection.HTTP_OK)
        server.enqueue(fakeResponse)
    }

    private fun ProductPageResponse.toEncodedString(): String {
        return json.encodeToString<ProductPageResponse>(this)
    }

    private fun ProductResponse.toEncodedString(): String {
        return json.encodeToString<ProductResponse>(this)
    }

    private fun String.toProductResponse(): ProductResponse {
        return json.decodeFromString<ProductResponse>(this)
    }

    private fun String.toProductPageResponse(): ProductPageResponse {
        return json.decodeFromString<ProductPageResponse>(this)
    }

    companion object {
        private val STUB_PRODUCTS: List<ProductResponse> =
            List(20) {
                listOf(
                    ProductResponse(
                        1,
                        1000,
                        "오둥이",
                        "https://item.kakaocdn.net/do/8fb89536158119f901780df1ba18493182f3bd8c9735553d03f6f982e10ebe70",
                    ),
                    ProductResponse(
                        2,
                        1000,
                        "오둥이",
                        "https://item.kakaocdn.net/do/8fb89536158119f901780df1ba18493182f3bd8c9735553d03f6f982e10ebe70",
                    ),
                    ProductResponse(
                        3,
                        1000,
                        "오둥오둥",
                        "https://item.kakaocdn.net/do/8fb89536158119f901780df1ba184931a88f7b2cbb72be0bdfff91ad65b168ab",
                    ),
                    ProductResponse(
                        4,
                        1000,
                        "꼬상",
                        "https://w7.pngwing.com/pngs/921/264/" +
                                "png-transparent-chipmunk-chip-n-dale-sticker-the-walt-disney-company-goofy-others.png",
                    ),
                    ProductResponse(
                        5,
                        1000,
                        "꼬상꼬상",
                        "https://i.namu.wiki/i/YvceZuAFsjYzbrTKYS09muExzVUw0f5JFBTAOLeCJbyeKghRLpkDnc5_XmQ9KvOpyRqz3zSWVZq5DpeW0HToWQ.webp",
                    ),
                )
            }.flatten()
                .mapIndexed { index, product ->
                    product.copy(
                        id = index + 1L,
                        name = "${product.name}${index + 1L}",
                    )
                }
    }
}