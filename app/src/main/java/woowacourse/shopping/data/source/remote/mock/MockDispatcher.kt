package woowacourse.shopping.data.source.remote.mock

import android.R.attr.path
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
import woowacourse.shopping.data.source.remote.mock.ProductData.products
import kotlin.Result.Companion.success

class MockDispatcher : Dispatcher() {
    private val productsArray = Json.parseToJsonElement(products).jsonArray

    override fun dispatch(request: RecordedRequest): MockResponse {
        val target = request.target
        val httpUrl = "http://localhost$target".toHttpUrl()
        val path = httpUrl.encodedPath

        return when {
            path == "/products" -> {
                val idsParam = httpUrl.queryParameter("ids")
                if (idsParam != null) {
                    val ids =
                        idsParam
                            .split(",")
                            .mapNotNull { it.trim().takeIf(String::isNotEmpty) }
                            .toSet()
                    val filtered =
                        productsArray.filter {
                            it.jsonObject["id"]?.jsonPrimitive?.content in ids
                        }
                    success(JsonArray(filtered).toString())
                } else {
                    val offset = httpUrl.queryParameter("offset")?.toIntOrNull() ?: 0
                    val limit = httpUrl.queryParameter("limit")?.toIntOrNull() ?: productsArray.size
                    if (offset >= productsArray.size) {
                        success("[]")
                    } else {
                        val sliced = productsArray.drop(offset).take(limit)
                        success(JsonArray(sliced).toString())
                    }
                }
            }
            path.startsWith("/products/") -> {
                val id = path.removePrefix("/products/")
                val product =
                    productsArray.firstOrNull {
                        it.jsonObject["id"]?.jsonPrimitive?.content == id
                    }
                if (product != null) {
                    success(Json.encodeToString(product))
                } else {
                    notFound()
                }
            }
            else -> notFound()
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
