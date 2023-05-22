package woowacourse.shopping.data.product

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import woowacourse.shopping.Product
import woowacourse.shopping.data.mapper.toProduct
import woowacourse.shopping.data.product.ProductMockWebServer.PORT
import woowacourse.shopping.data.product.ProductMockWebServer.startServer

object ProductService : ProductRemoteDataSource {
    init {
        startServer()
    }

    override fun findProductById(id: Int): Product {
        var product: Product?
        var number = 0
        while (number < 400) {
            product = getProductsWithRange(number, 20).find { it.id == id }
            if (product != null) return product
            number += 20
        }
        return Product.defaultProduct
    }

    override fun getProductsWithRange(start: Int, range: Int): List<Product> {
        var newProducts: List<Product> = emptyList()
        val thread = Thread {
            val client = OkHttpClient()
            val host = "http://localhost:$PORT/"
            val path = "products?start=$start&range=$range"
            val request = Request.Builder().url(host + path).build()
            val response = client.newCall(request).execute()
            val body = response.body?.string() ?: return@Thread
            newProducts = parseResponse(body)
        }
        thread.start()
        thread.join()
        return newProducts
    }

    private fun parseResponse(responseBody: String?): List<Product> {
        return responseBody?.let {
            val productsJsonArray = JSONArray(it)
            val products = mutableListOf<Product>()
            for (item in 0 until productsJsonArray.length()) {
                val jsonObject = productsJsonArray.getJSONObject(item)
                products.add(jsonObject.toProduct())
            }
            products
        } ?: emptyList()
    }
}
