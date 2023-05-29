package woowacourse.shopping.data

import com.domain.model.Product
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

object ProductService {
    private val okHttpClient = OkHttpClient()

    fun request(
        url: String,
        onSuccess: (List<Product>) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        val request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onFailure(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    response.close()

                    val result = responseBody?.let {
                        parseJsonToProducts(responseBody)
                    } ?: emptyList()

                    onSuccess(result)
                }
            },
        )
    }

    private fun parseJsonToProducts(json: String): List<Product> {
        val products = mutableListOf<Product>()
        val jsonProducts = JSONObject(json).getJSONObject("data").getJSONArray("products")

        for (i in 0 until jsonProducts.length()) {
            val jsonProduct: JSONObject = jsonProducts.getJSONObject(i)
            val product: Product = Gson().fromJson(jsonProduct.toString(), Product::class.java)

            products.add(product)
        }

        return products
    }
}
