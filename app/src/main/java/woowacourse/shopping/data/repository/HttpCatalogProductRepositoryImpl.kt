package woowacourse.shopping.data.repository

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
    private var cachedProducts: List<ProductUiModel>? = null

    private fun getAllProducts(callback: (List<ProductUiModel>) -> Unit) {
        if (cachedProducts != null) {
            callback(cachedProducts!!)
            return
        }

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

                cachedProducts = products
                callback(products)
            } catch (e: Exception) {
                e.printStackTrace()
                callback(emptyList())
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
