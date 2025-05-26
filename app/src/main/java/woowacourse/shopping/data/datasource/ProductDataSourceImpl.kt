package woowacourse.shopping.data.datasource

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.server.ProductServer
import woowacourse.shopping.domain.Product
import kotlin.concurrent.thread

class ProductDataSourceImpl(
    private val productServer: ProductServer,
    private val client: OkHttpClient,
    private val gson: Gson,
) : ProductDataSource {
    private lateinit var products: List<Product>

    init {
        thread {
            loadProducts()
        }.join()
    }

    override fun get(id: Long): Product = products.find { id == it.id } ?: throw IllegalArgumentException()

    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = (page - 1) * pageSize
        val toIndex = minOf(fromIndex + pageSize, products.size)
        return products.subList(fromIndex, toIndex)
    }

    override fun notHasMoreProduct(
        page: Int,
        pageSize: Int,
    ): Boolean {
        val fromIndex = page * pageSize
        return fromIndex >= products.size
    }

    private fun loadProducts() {
        val baseUrl = productServer.mockWebServer.url("/")

        val request =
            Request.Builder()
                .url(baseUrl.resolve("/products")!!)
                .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string() ?: return
        products = gson.fromJson(responseBody, Array<Product>::class.java).toList()
    }
}
