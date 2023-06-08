package woowacourse.shopping.data.product

import com.example.domain.Product
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MockProductRemoteService {

    private val okHttpClient = OkHttpClient()

    fun requestProduct(
        productId: Long,
        onSuccess: (Product?) -> Unit,
        onFailure: () -> Unit
    ) {
        val baseUrl = String.format("http://localhost:%s", MockWebServer.get().port)
        val url = "$baseUrl/products?id=$productId"
        val request = Request.Builder().url(url).build()

        okHttpClient.newCall(request).enqueue(
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onFailure()
                }

                override fun onResponse(call: Call, response: Response) {
                    if (400 <= response.code) return onFailure()
                    val responseBody = response.body?.string()
                    response.close()

                    val result = responseBody?.let {
                        parseJsonToProductList2(it)
                    }

                    onSuccess(result)
                }
            })
    }

    fun requestProductsUnit(
        lastProductId: Long,
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    ) {
        val baseUrl = String.format("http://localhost:%s", MockWebServer.get().port)
        val url = "$baseUrl/products?lastProductId=$lastProductId"
        val request = Request.Builder().url(url).build()

        okHttpClient.newCall(request).enqueue(
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onFailure()
                }

                override fun onResponse(call: Call, response: Response) {
                    if (400 <= response.code) return onFailure()
                    val responseBody = response.body?.string()
                    response.close()

                    val result = responseBody?.let {
                        parseJsonToProductList(it)
                    } ?: emptyList()

                    onSuccess(result)
                }
            })
    }

    private fun parseJsonToProductList(responseString: String): List<Product> {
        val products: MutableList<Product> = mutableListOf()

        val jsonArray = JSONArray(responseString)
        for (index in 0 until jsonArray.length()) {
            val jsonProduct = jsonArray.getJSONObject(index)
            val id = jsonProduct.getInt("id")
            val name = jsonProduct.getString("name")
            val imageUrl = jsonProduct.getString("imageUrl")
            val price = jsonProduct.getInt("price")

            val product = Product(id = id, name = name, imageUrl = imageUrl, price = price)
            products.add(product)
        }

        return products
    }

    private fun parseJsonToProductList2(responseString: String): Product {
        val jsonObject = JSONObject(responseString)
        val id = jsonObject.getInt("id")
        val name = jsonObject.getString("name")
        val imageUrl = jsonObject.getString("imageUrl")
        val price = jsonObject.getInt("price")

        val product = Product(id = id, name = name, imageUrl = imageUrl, price = price)

        return product
    }
}
