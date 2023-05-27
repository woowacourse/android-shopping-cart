package woowacourse.shopping.data

import android.os.Looper
import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

class ProductFakeRepository(url: String) : ProductRepository {

    private val url = url.dropLast(1)
    private val okHttpClient = OkHttpClient()
    private var offset = 0

    override fun getAll(
        onSuccess: (List<Product>) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        val request = Request.Builder().url("$url/products").build()
        getResponse(request, onSuccess, onFailure)
    }

    override fun getNext(
        count: Int,
        onSuccess: (List<Product>) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        val request = Request.Builder().url("$url/products?offset=$offset&count=$count").build()
        getResponse(request, onSuccess = {
            offset += count
            onSuccess(it)
        }, onFailure)
    }

    override fun findById(
        id: Long,
        onSuccess: (Product) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        val request = Request.Builder().url("$url/products/$id").build()
        getResponse(request, { products ->
            val product = products.firstOrNull { it.id == id }
            if (product != null) {
                onSuccess(product)
            } else {
                onFailure(IllegalArgumentException("해당하는 아이템이 없습니다."))
            }
        }, onFailure)
    }

    private fun getResponse(
        request: Request,
        onSuccess: (List<Product>) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        val handler = android.os.Handler(Looper.getMainLooper())

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler.post {
                    onFailure(e)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val products = ProductJsonParser.parse(responseBody)
                    handler.post {
                        onSuccess(products)
                    }
                } else {
                    handler.post {
                        onFailure(Exception("Response unsuccessful"))
                    }
                }
            }
        })
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
