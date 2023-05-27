package woowacourse.shopping.data.product

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import woowacourse.shopping.model.Product
import java.lang.Thread.sleep

class ProductRepositoryImpl(baseUrl: String) : ProductDataSource {
    private val baseUrl = baseUrl.dropLast(1)
    private val client = OkHttpClient()

    override fun findProductById(id: Long): Product {
        val request = Request.Builder()
            .url("$baseUrl/products/$id")
            .build()

        return executeRequest(request).let { parseResponse(it) }.firstOrNull { it.id == id }
            ?: throw RuntimeException("Product not found with id: $id")
    }

    override fun getProductsWithRange(startIndex: Int, size: Int): List<Product> {
        val request = Request.Builder()
            .url("$baseUrl/products?offset=$startIndex&count=$size")
            .build()

        return executeRequest(request).let { parseResponse(it) }
    }

    private fun executeRequest(request: Request): String? {
        var responseBody: String? = null
        client.newCall(request).enqueue(
            object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                    throw RuntimeException("Request failed", e)
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    responseBody = response.body?.string()
                    response.close()
                }
            },
        )

        while (responseBody == null) {
            sleep(1)
        }
        return responseBody
    }

    private fun parseResponse(responseBody: String?): List<Product> {
        return responseBody?.let {
            val productsJsonArray = JSONArray(it)
            val products = mutableListOf<Product>()
            for (i in 0 until productsJsonArray.length()) {
                val productJson = productsJsonArray.getJSONObject(i)
                val product = productJson.toProduct()
                products.add(product)
            }
            products
        } ?: emptyList()
    }
}
