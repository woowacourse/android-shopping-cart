package woowacourse.shopping.data.product.mockserver

import com.google.gson.Gson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.product.ProductDatabase

object MockServer {
    private val gson = Gson()

    private val dispatcher =
        object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                val path = request.path ?: return MockResponse().setResponseCode(404)
                return when {
                    path.startsWith("/products/page/") -> {
                        val (offset, pagingSize) =
                            path.removePrefix("/products/page/")
                                .split("/")
                                .map { it.toInt() }
                        val result =
                            if (offset >= ProductDatabase.products.size) {
                                emptyList()
                            } else {
                                ProductDatabase.products.drop(offset)
                            }.take(pagingSize)

                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(gson.toJson(result))
                    }
                    path.startsWith("/products/") -> {
                        val productId = request.path?.removePrefix("/products/")?.toLongOrNull() ?: -1
                        val foundProduct =
                            ProductDatabase.products.find { it.id == productId }
                                ?: return MockResponse().setResponseCode(404)

                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(gson.toJson(foundProduct))
                    }
                    path == "/products/count" -> {
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(gson.toJson(ProductDatabase.products.size))
                    }
                    else -> {
                        MockResponse().setResponseCode(404)
                    }
                }
            }
        }

    val mockWebServer =
        MockWebServer().apply {
            this.dispatcher = MockServer.dispatcher
        }
}
