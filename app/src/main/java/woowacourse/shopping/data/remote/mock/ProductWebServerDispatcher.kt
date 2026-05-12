package woowacourse.shopping.data.remote.mock

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import mockwebserver3.Dispatcher
import mockwebserver3.MockResponse
import mockwebserver3.RecordedRequest
import okhttp3.HttpUrl.Companion.toHttpUrl
import woowacourse.shopping.data.remote.mock.MockProducts.products

class ProductWebServerDispatcher : Dispatcher() {
    private val mockProducts = Json.parseToJsonElement(products).jsonArray

    override fun dispatch(request: RecordedRequest): MockResponse {
        val target = request.target
        val httpUrl = "http://localhost$target".toHttpUrl()
        val path = httpUrl.encodedPath

        return when {
            path == "/products" -> {
                val offset = httpUrl.queryParameter("offset")?.toIntOrNull() ?: 0
                val limit = httpUrl.queryParameter("limit")?.toIntOrNull() ?: 0
                val body =
                    if (offset >= mockProducts.size) {
                        "[]"
                    } else {
                        JsonArray(mockProducts.drop(offset).take(limit)).toString()
                    }

                success(body)
            }

            path.startsWith("/products/") -> {
                val id = path.removePrefix("/products/")
                val product =
                    mockProducts.firstOrNull {
                        it.jsonObject["id"]?.jsonPrimitive?.content == id
                    }

                if (product != null) {
                    success(Json.encodeToString(product))
                } else {
                    notFound()
                }
            }

            else -> {
                notFound()
            }
        }
    }

    private fun success(body: String): MockResponse =
        MockResponse
            .Builder()
            .addHeader("Content-Type", "application/json")
            .code(200)
            .body(body)
            .build()

    private fun notFound(): MockResponse =
        MockResponse
            .Builder()
            .code(404)
            .build()
}
