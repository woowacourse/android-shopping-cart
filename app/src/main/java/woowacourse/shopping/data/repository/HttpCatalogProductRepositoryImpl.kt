package woowacourse.shopping.data.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.product.catalog.ProductUiModel
import kotlin.concurrent.thread

class HttpCatalogProductRepositoryImpl(
    private val baseUrl: String,
    private val client: OkHttpClient = OkHttpClient(),
) : CatalogProductRepository {
    private fun getAllProducts(callback: (List<ProductUiModel>) -> Unit) {
        thread {
            try {
                val request =
                    Request
                        .Builder()
                        .url("$baseUrl/products")
                        .build()

                val response = client.newCall(request).execute()
                val body = response.body?.string() ?: ""
                val gson = Gson()
                val type = object : TypeToken<List<ProductUiModel>>() {}.type
                val products = gson.fromJson<List<ProductUiModel>>(body, type)

                Log.d("HTTP_GET", "$products")
                callback(products)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("HTTP_GET", "Failed.")
                callback(emptyList()) // 실패 시 빈 리스트 반환
            }
        }
    }

    override fun getAllProductsSize(callback: (Int) -> Unit) {
        getAllProducts { products ->
            callback(products.size)
        }
    }

    override fun getProductsInRange(
        startIndex: Int,
        endIndex: Int,
        callback: (List<ProductUiModel>) -> Unit,
    ) {
        getAllProducts { products ->
            callback(products.subList(startIndex, minOf(endIndex, products.size)))
        }
    }

    override fun getCartProductsByUids(
        uids: List<Int>,
        callback: (List<ProductUiModel>) -> Unit,
    ) {
        getAllProducts { products ->
            callback(products.filter { it.id in uids })
        }
    }
}
