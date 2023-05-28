package woowacourse.shopping.domain.repository

import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product
import java.io.IOException
import java.util.concurrent.TimeUnit

class ProductRemoteRepository(baseUrl: String) : ProductRepository {
    private val baseUrl = baseUrl.dropLast(1)
    private val client =
        OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .build()

    override fun findAll(): List<Product> {
        val request = Request.Builder().url("$baseUrl/products").build()
        return parseResponse(executeRequest(request))
    }

    override fun find(id: Int): Product {
        val request = Request.Builder().url("$baseUrl/products/$id").build()
        return parseResponse(executeRequest(request)).first()
    }

    override fun findRange(mark: Int, rangeSize: Int): List<Product> {
        val request = Request.Builder().url("$baseUrl/products?mark=$mark&size=$rangeSize").build()
        return parseResponse(executeRequest(request))
    }

    override fun isExistByMark(mark: Int): Boolean {
        val request = Request.Builder().url("$baseUrl/products").build()
        return parseResponse(executeRequest(request)).size > mark
    }

    private fun executeRequest(request: Request): String? {
        var responseBody: String? = null
        client.newCall(request).enqueue(
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    throw java.lang.RuntimeException("Request Failed", e)
                }

                override fun onResponse(call: Call, response: Response) {
                    responseBody = response.body?.string()
                    response.close()
                }
            }
        )

        while (responseBody == null) {
            Thread.sleep(1)
        }
        return responseBody
    }

    private fun parseResponse(responseBody: String?): List<Product> {
        return responseBody?.let {
            val productsJSONArray = JSONArray(it)
            val products = mutableListOf<Product>()
            for (index in 0 until productsJSONArray.length()) {
                val productJSON = productsJSONArray.getJSONObject(index)
                val product = Product.fromJson(productJSON)
                products.add(product)
            }
            products
        } ?: emptyList()
    }
}

private fun Product.Companion.fromJson(productJSON: JSONObject): Product {
    return Product(
        productJSON.getInt("id"),
        productJSON.getString("name"),
        productJSON.getString("imageUrl"),
        Price(productJSON.getInt("price")),
    )
}
