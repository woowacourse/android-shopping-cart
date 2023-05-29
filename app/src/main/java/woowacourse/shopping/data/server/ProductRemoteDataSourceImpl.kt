package woowacourse.shopping.data.server

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.URL
import java.io.IOException
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

class ProductRemoteDataSourceImpl : ProductRemoteDataSource {
    private val okHttpClient = OkHttpClient()

    init {
        thread {
            startMockWebServer()
        }
    }

    override fun getProducts(path: String): List<Product> {
        val url = "http://localhost:8080/products/$path"
        val request = Request.Builder().url(url).build()
        val products = mutableListOf<Product>()

        val countDownLatch = CountDownLatch(1)
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                countDownLatch.countDown()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200) {
                    val input = response.body?.string()
                    val jsonArray = JSONArray(input)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        products.add(createProduct(jsonObject))
                    }
                }
                countDownLatch.countDown()
            }
        })

        countDownLatch.await()
        return products
    }

    private fun createProduct(response: JSONObject): Product {
        return Product(
            picture = URL(response.getString("imageUrl")),
            title = response.getString("name"),
            price = response.getInt("price")
        )
    }
}
