package woowacourse.shopping.server

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.cart.server.CartService
import woowacourse.shopping.data.cart.server.DummyCarts
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Carts

class TestCartServiceImpl : CartService {
    private val gson = Gson()
    private val client = OkHttpClient()
    val server = MockWebServer()

    init {
        server.dispatcher =
            object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    val path = request.requestUrl?.encodedPath ?: ""
                    val query = request.requestUrl?.query
                    return when {
                        path == BASE_PATH && query == null -> {
                            val carts = DummyCarts.getCarts()
                            val json = gson.toJson(carts)
                            MockResponse()
                                .setBody(json)
                                .setResponseCode(SUCCESS_RESPONSE_CODE)
                                .addHeader("Content-Type", CONTENT_TYPE)
                        }
                        path == BASE_PATH && query != null -> {
                            val params =
                                query.split("&").associate {
                                    val (k, v) = it.split("=")
                                    k to v
                                }
                            val limit = params[QUERY_PARAM_LIMIT]?.toIntOrNull() ?: 0
                            val offset = params[QUERY_PARAM_OFFSET]?.toIntOrNull() ?: 0
                            val paged =
                                DummyCarts
                                    .getCarts()
                                    .carts
                                    .drop(offset)
                                    .take(limit)
                            val totalQuantity = paged.sumOf { it.quantity }
                            val result = Carts(paged, totalQuantity)
                            val json = gson.toJson(result)
                            MockResponse()
                                .setBody(json)
                                .setResponseCode(SUCCESS_RESPONSE_CODE)
                                .addHeader("Content-Type", CONTENT_TYPE)
                        }
                        path.startsWith("$BASE_PATH/") -> {
                            val idStr = path.substringAfterLast("/")
                            val id = idStr.toLongOrNull()
                            val cart = DummyCarts.getCarts().carts.find { it.goods.id == id }
                            if (cart != null) {
                                val json = gson.toJson(cart)
                                MockResponse()
                                    .setBody(json)
                                    .setResponseCode(SUCCESS_RESPONSE_CODE)
                                    .addHeader("Content-Type", CONTENT_TYPE)
                            } else {
                                MockResponse().setResponseCode(NOT_FOUND_RESPONSE_CODE)
                            }
                        }
                        else -> MockResponse().setResponseCode(NOT_FOUND_RESPONSE_CODE)
                    }
                }
            }
        server.start()
    }

    override fun getAll(): Carts {
        val url = server.url(BASE_PATH).toString()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        val body = response.body?.string() ?: throw IllegalStateException(ERROR_MESSAGE)
        return gson.fromJson(body, Carts::class.java)
    }

    override fun getCartById(id: Long): Cart? {
        val url = server.url("$BASE_PATH/$id").toString()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) return null
        val body = response.body?.string() ?: throw IllegalStateException(ERROR_MESSAGE)
        return gson.fromJson(body, Cart::class.java)
    }

    override fun getPagedCarts(
        limit: Int,
        offset: Int,
    ): Carts {
        val url = server.url("$BASE_PATH?limit=$limit&offset=$offset").toString()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        val body = response.body?.string() ?: throw IllegalStateException(ERROR_MESSAGE)
        return gson.fromJson(body, Carts::class.java)
    }

    private companion object {
        private const val BASE_PATH = "/carts"
        private const val SUCCESS_RESPONSE_CODE = 200
        private const val ERROR_MESSAGE = "응답 없음"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_OFFSET = "offset"
        private const val CONTENT_TYPE = "application/json"
        private const val NOT_FOUND_RESPONSE_CODE = 404
    }
}
