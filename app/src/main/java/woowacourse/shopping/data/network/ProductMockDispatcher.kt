package woowacourse.shopping.data.network

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.json.JSONArray
import org.json.JSONObject
import woowacourse.shopping.data.productData
import woowacourse.shopping.model.Product

class ProductMockDispatcher : Dispatcher() {
    private val products = productData()

    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.requestUrl?.encodedPath
        return when {
            path == "/products" -> {
                jsonResponse(products.toJsonArray().toString())
            }

            path?.startsWith("/products/") == true -> {
                val id = path.removePrefix("/products/")
                val product = products.firstOrNull { it.id == id }

                if (product == null) {
                    notFoundResponse()
                } else {
                    jsonResponse(product.toJson().toString())
                }
            }

            else -> notFoundResponse()
        }
    }

    private fun List<Product>.toJsonArray(): JSONArray =
        JSONArray().apply {
            forEach { product ->
                put(product.toJson())
            }
        }

    private fun Product.toJson(): JSONObject =
        JSONObject()
            .put("id", id.toLong())
            .put("name", getName())
            .put("price", getPrice())
            .put("imageUrl", imageUrl)

    private fun jsonResponse(body: String): MockResponse =
        MockResponse()
            .setHeader("Content-Type", "application/json")
            .setResponseCode(200)
            .setBody(body)

    private fun notFoundResponse(): MockResponse = MockResponse().setResponseCode(404)
}
