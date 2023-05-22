package woowacourse.shopping.data

import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.lang.Thread.sleep

class ProductFakeRepository(url: String) : ProductRepository {

    private val url = url.dropLast(1)
    private val okHttpClient = OkHttpClient()
    private var offset = 0

    private val products: List<Product>
        get() = Request.Builder().url("$url/products").build().let { request ->
            getResponse(request)?.let { response -> ProductJsonParser.parse(response) }
                ?: throw IllegalArgumentException("해당하는 아이템이 없습니다.")
        }

    override fun getAll(): List<Product> {
        return products.toList()
    }

    override fun getNext(count: Int): List<Product> {
        val request = Request.Builder().url("$url/products?offset=$offset&count=$count").build()
        val products = getResponse(request).let { response -> ProductJsonParser.parse(response) }
        offset += products.size
        return products
    }

    override fun findById(id: Long): Product {
        Request.Builder().url("$url/products/$id").build().let { request ->
            getResponse(request)?.let { response ->
                return ProductJsonParser.parse(response).firstOrNull { it.id == id }
                    ?: throw IllegalArgumentException("해당하는 아이템이 없습니다.")
            } ?: throw IllegalArgumentException("해당하는 아이템이 없습니다.")
        }
    }

    private fun getResponse(request: Request): String? {
        var responseBody: String? = null
        okHttpClient.newCall(request).enqueue(
            object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                    throw e
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
}

object ProductJsonParser {
    fun parse(json: String?): List<Product> {
        return json?.let {
            val productsJsonArray = JSONArray(it)
            val products = mutableListOf<Product>()
            for (i in 0 until productsJsonArray.length()) {
                val productJson = productsJsonArray.getJSONObject(i)
                val product = Product(
                    productJson.getLong("id"),
                    productJson.getString("name"),
                    productJson.getInt("price"),
                    productJson.getString("imageUrl"),
                )
                products.add(product)
            }
            products
        } ?: emptyList()
    }
}
