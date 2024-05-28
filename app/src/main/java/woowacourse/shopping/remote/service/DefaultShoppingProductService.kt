package woowacourse.shopping.remote.service

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.IOException
import woowacourse.shopping.data.common.ioExecutor
import woowacourse.shopping.remote.model.ProductPageResponse
import woowacourse.shopping.remote.model.ProductResponse
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit

class DefaultShoppingProductService(
    private val executor: ExecutorService = ioExecutor,
    private val products: List<ProductResponse> = STUB_PRODUCTS,
) :
    ProductService {
    private val json: Json = Json { ignoreUnknownKeys = true }
    private val server: MockWebServer = MockWebServer()

    init {
        executor.submit { server.start() }[TIME_OUT, TimeUnit.SECONDS]
    }

    override fun fetchProducts(
        currentPage: Int,
        size: Int,
    ): ProductPageResponse {
        return executor.submit(
            Callable {
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
            },
        )[TIME_OUT, TimeUnit.SECONDS]
    }

    override fun fetchProductById(id: Long): ProductResponse {
        return executor.submit(
            Callable {
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
            },
        )[TIME_OUT, TimeUnit.SECONDS]
    }

    override fun canLoadMore(
        page: Int,
        size: Int,
    ): Boolean {
        return executor.submit(
            Callable {
                (products.size > page * size) || (page < 1)
            },
        )[TIME_OUT, TimeUnit.SECONDS]
    }

    private fun pushProducts(
        currentPage: Int,
        size: Int,
    ) {
        val startIdx = (currentPage - 1) * size
        val pagedProducts = products.subList(startIdx, startIdx + size)
        val pageResponse =
            ProductPageResponse(
                pageNumber = currentPage,
                content = pagedProducts,
                totalPages = products.size / size,
                pageSize = size,
                totalElements = products.size,
            )
        val fakeResponse =
            MockResponse().setHeader("Content-Type", "application/json")
                .setBody(pageResponse.toEncodedString()).setResponseCode(HttpURLConnection.HTTP_OK)
        server.enqueue(fakeResponse)
    }

    private fun pushProduct(product: ProductResponse) {
        val fakeResponse =
            MockResponse().setHeader("Content-Type", "application/json")
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

    private fun shutdown() {
        server.shutdown()
    }

    companion object {
        private const val TIME_OUT = 3L

        @Volatile
        private var instance: ProductService? = null

        fun instance(): ProductService =
            instance ?: synchronized(this) {
                instance ?: synchronized(this) {
                    DefaultShoppingProductService().also { instance = it }
                }
            }

        fun shutdown() {
            instance?.let {
                if (it is DefaultShoppingProductService) {
                    it.shutdown()
                }
            }
        }
    }
}
