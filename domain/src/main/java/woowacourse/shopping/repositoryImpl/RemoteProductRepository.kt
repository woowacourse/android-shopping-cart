package woowacourse.shopping.repositoryImpl

import java.lang.Thread.sleep
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.ProductRepository

class RemoteProductRepository(baseUrl: String) : ProductRepository {
    private val baseUrl = baseUrl.dropLast(1)
    private val client = OkHttpClient()

    private var offset = 0

    override fun getAll(): List<Product> {
        val request = Request.Builder()
            .url("$baseUrl/products")
            .build()
        return executeRequest(request).let { parseResponse(it) }
    }

    override fun getNext(count: Int): List<Product> {
        val request = Request.Builder()
            .url("$baseUrl/products?offset=$offset&count=$count")
            .build()

        val products = executeRequest(request).let { parseResponse(it) }
        offset += products.size
        return products
    }

    override fun insert(product: Product): Int {
        val request = Request.Builder()
            .url("$baseUrl/products")
            .post(product.toJson().toRequestBody(JSON_MEDIA_TYPE))
            .build()

        return executeRequest(request)
            ?.let { JSONArray(it).getJSONObject(0).getInt("id") }
            ?: throw RuntimeException("Product insert failed")
    }

    override fun findById(id: Int): Product {
        val request = Request.Builder()
            .url("$baseUrl/products/$id")
            .build()

        return executeRequest(request).let { parseResponse(it) }.firstOrNull { it.id == id }
            ?: throw RuntimeException("Product not found with id: $id")
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
            }
        )

        while (responseBody == null) { sleep(1) }
        return responseBody
    }

    private fun parseResponse(responseBody: String?): List<Product> {
        return responseBody?.let {
            val productsJsonArray = JSONArray(it)
            val products = mutableListOf<Product>()
            for (i in 0 until productsJsonArray.length()) {
                val productJson = productsJsonArray.getJSONObject(i)
                val product = Product.fromJson(productJson)
                products.add(product)
            }
            products
        } ?: emptyList()
    }

    companion object {
        private val JSON_MEDIA_TYPE = "application/json".toMediaTypeOrNull()
    }
}
