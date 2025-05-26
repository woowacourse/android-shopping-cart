package woowacourse.shopping.fixture

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.product.ProductsDataSource
import woowacourse.shopping.mapper.toUiModel
import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.product.catalog.model.Product

class FakeCatalogItemRepository(
    private val baseUrl: String,
) : ProductsDataSource {
    private val gson = Gson()
    private val client = OkHttpClient()

    private fun fetchProducts(): List<ProductUiModel> {
        val request = Request.Builder().url("$baseUrl/products").build()
        val response = client.newCall(request).execute()
        val body = response.body?.string()

        if (body.isNullOrBlank()) {
            return emptyList()
        }

        val parsed = gson.fromJson(body, Array<Product>::class.java)
        return parsed.map { it.toUiModel() }
    }

    override fun getProducts(): List<ProductUiModel> = fetchProducts()

    override fun getSubListedProducts(
        startIndex: Int,
        lastIndex: Int,
    ): List<ProductUiModel> {
        val all = fetchProducts()
        return all.subList(
            startIndex.coerceAtLeast(0),
            lastIndex.coerceAtMost(all.size),
        )
    }

    override fun getProductsSize(): Int = fetchProducts().size
}
