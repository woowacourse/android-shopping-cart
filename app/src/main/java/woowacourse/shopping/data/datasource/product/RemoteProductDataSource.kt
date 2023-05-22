package woowacourse.shopping.data.datasource.product

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import woowacourse.shopping.data.model.Page
import woowacourse.shopping.data.model.Price
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.server.GET
import woowacourse.shopping.server.ShoppingMockWebServer
import java.io.IOException
import java.util.concurrent.CountDownLatch

class RemoteProductDataSource : ProductDataSource.Remote {
    private val shoppingService: ShoppingMockWebServer = ShoppingMockWebServer()
    private var BASE_URL: String

    init {
        shoppingService.start()
        shoppingService.join()
        BASE_URL = shoppingService.baseUrl
    }

    override fun getProductByPage(page: Page): List<Product> {
        shoppingService.join()
        val url = "${BASE_URL}/products?start=${page.start}&count=${page.sizePerPage}"
        val httpClient = OkHttpClient()
        val request = Request.Builder().url(url).method(GET, null).build()
        val products = mutableListOf<Product>()
        val countDownLatch = CountDownLatch(1)

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                countDownLatch.countDown()
            }

            override fun onResponse(call: Call, response: Response) {
                val input = response.body?.string()
                val jsonArray = JSONArray(input)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    products.add(convertToProduct(jsonObject))
                }
                countDownLatch.countDown()
            }
        })

        countDownLatch.await()
        return products
    }

    override fun findProductById(id: Int): Product? {
        shoppingService.join()
        val url = "${BASE_URL}/products?productId=${id}"
        val httpClient = OkHttpClient()
        val request = Request.Builder().url(url).method(GET, null).build()
        val countDownLatch = CountDownLatch(1)
        var product: Product? = null

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                countDownLatch.countDown()
            }

            override fun onResponse(call: Call, response: Response) {
                val input = response.body?.string()
                val jsonObject = JSONObject(input)
                if (jsonObject.getInt("id") == id) {
                    product = convertToProduct(jsonObject)
                }
                countDownLatch.countDown()
            }
        })

        countDownLatch.await()
        return product
    }

    private fun convertToProduct(response: JSONObject): Product = Product(
        id = response.getInt("id"),
        imageUrl = response.getString("imageUrl"),
        name = response.getString("name"),
        price = Price(response.getInt("price"))
    )
}
