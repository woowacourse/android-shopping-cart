package woowacourse.shopping.data.repository

import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import woowacourse.shopping.product.catalog.ProductUiModel

class MockCatalogProductRepositoryImpl(
    private val baseUrl: HttpUrl,
    private val client: OkHttpClient = OkHttpClient(),
    private val gson: Gson = Gson(),
) : CatalogProductRepository {
    private var cachedProducts: List<ProductUiModel>? = null

    override fun getAllProductsSize(): Int = getAllProducts().size

    override fun getProductsInRange(
        startIndex: Int,
        endIndex: Int,
    ): List<ProductUiModel> {
        val all = getAllProducts()
        return all.subList(startIndex, minOf(endIndex, all.size))
    }

    override fun getCartProductsByUids(uids: List<Int>): List<ProductUiModel> {
        val all = getAllProducts()
        return uids.mapNotNull { id -> all.find { it.id == id } }
    }

    private fun getAllProducts(): List<ProductUiModel> {
        if (cachedProducts != null) return cachedProducts!!

        val request =
            Request
                .Builder()
                .url(baseUrl.resolve("/products")!!)
                .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val body = response.body?.string() ?: throw IOException("Empty body")
            val products = gson.fromJson(body, Array<ProductUiModel>::class.java).toList()
            cachedProducts = products
            return products
        }
    }
}
