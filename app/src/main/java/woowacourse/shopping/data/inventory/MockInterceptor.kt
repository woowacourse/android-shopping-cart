package woowacourse.shopping.data.inventory

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor : Interceptor {
    private val gson = Gson()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val uri = request.url.toUri()
        val method = request.method

        if (uri.path == "/product-items" && method == "GET") {
            val productItemsJson = gson.toJson(DummyProducts.products)
            return Response.Builder()
                .code(200)
                .message("OK")
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .header("Content-Type", "application/json")
                .body(productItemsJson.toResponseBody("application/json".toMediaTypeOrNull()))
                .build()
        }

        if (uri.path.startsWith("/product-items/") && method == "GET") {
            return try {
                val productIdString = uri.path.substringAfterLast('/')
                val productId = productIdString.toInt()
                val productItem = DummyProducts.products.find { it.id == productId }

                if (productItem != null) {
                    val productItemJson = gson.toJson(productItem)
                    Response.Builder()
                        .code(200)
                        .message("OK")
                        .request(request)
                        .protocol(Protocol.HTTP_1_1)
                        .header("Content-Type", "application/json")
                        .body(productItemJson.toResponseBody("application/json".toMediaTypeOrNull()))
                        .build()
                } else {
                    Response.Builder()
                        .code(404)
                        .message("Not Found")
                        .request(request)
                        .protocol(Protocol.HTTP_1_1)
                        .body("{\"error\":\"ProductItem not found\"}".toResponseBody("application/json".toMediaTypeOrNull()))
                        .build()
                }
            } catch (e: NumberFormatException) {
                Response.Builder()
                    .code(400)
                    .message("Bad Request")
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .body("{\"error\":\"Invalid product ID format\"}".toResponseBody("application/json".toMediaTypeOrNull()))
                    .build()
            }
        }

        if (uri.path == "/product-items" && method == "POST") {
            val successResponseJson =
                gson.toJson(
                    mapOf(
                        "status" to "success",
                        "message" to "ProductItem processed (mocked)",
                    ),
                )
            return Response.Builder()
                .code(201)
                .message("Created")
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .header("Content-Type", "application/json")
                .body(successResponseJson.toResponseBody("application/json".toMediaTypeOrNull()))
                .build()
        }

        return Response.Builder()
            .code(501)
            .message("Not Implemented")
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .body("{\"error\":\"Endpoint not mocked: ${uri.path}\"}".toResponseBody("application/json".toMediaTypeOrNull()))
            .build()
    }
}
