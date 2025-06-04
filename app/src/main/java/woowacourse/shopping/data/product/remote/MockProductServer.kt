package woowacourse.shopping.data.product.remote

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.common.convertToJson
import woowacourse.shopping.data.product.storage.VolatileProductRemoteDataStorage.getProductsById
import woowacourse.shopping.data.product.storage.VolatileProductRemoteDataStorage.load
import java.net.URI
import kotlin.concurrent.thread

class MockProductServer {
    val mockWebServer = MockWebServer()

    fun start(port: Int) {
        thread {
            mockWebServer.start(port)
        }
        val dispatcher =
            object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse =
                    when {
                        request.path?.removePrefix("/products/")?.toLongOrNull() != null -> {
                            val productId = request.path?.substringAfterLast("/")?.toLong()
                            val product = getProductsById(productId ?: error("Invalid ID"))
                            val json = convertToJson(product)

                            MockResponse()
                                .setHeader("Content-Type", "application/json")
                                .setResponseCode(200)
                                .setBody(json)
                        }

                        request.path?.startsWith("/products") == true -> {
                            val uri = URI("http://localhost${request.path}")
                            val queryParams =
                                uri.query
                                    ?.split("&")
                                    ?.associate {
                                        val (key, value) = it.split("=")
                                        key to value
                                    }.orEmpty()

                            val lastId = queryParams["lastProductId"]?.toLongOrNull()
                            val size = queryParams["size"]?.toIntOrNull() ?: 10
                            val filtered = load(lastId, size)
                            val json = convertToJson(filtered)

                            MockResponse()
                                .setHeader("Content-Type", "application/json")
                                .setResponseCode(200)
                                .setBody(json)
                        }

                        else -> {
                            MockResponse().setResponseCode(404)
                        }
                    }
            }

        mockWebServer.dispatcher = dispatcher
    }

    fun shutDown() {
        mockWebServer.shutdown()
    }
}
