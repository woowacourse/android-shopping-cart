package woowacourse.shopping.data

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class ProductsHttpClient(
    private val baseUrl: String,
) {
    private val client: OkHttpClient = OkHttpClient()

    fun getProducts(): Response = run("/products")

    fun getProductById(id: Long): Response = run("/products/$id")

    fun getCartItems(): Response = run("/cart-items")

    private fun run(path: String): Response {
        val request: Request =
            Request
                .Builder()
                .url(baseUrl + path)
                .build()

        return client.newCall(request).execute()
    }
}
