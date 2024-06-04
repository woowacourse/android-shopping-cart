package woowacourse.shopping.data.remote

import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.model.product.CartableProduct
import woowacourse.shopping.data.util.convertJsonToList
import woowacourse.shopping.data.util.convertJsonToObject

class ProductRemoteDataSource(
    private val baseUrl: String = BASE_URL.dropLast(1),
) : ProductDataSource {
    private val client = OkHttpClient()

    override fun fetchSinglePage(page: Int): List<CartableProduct> {
        val request =
            Request.Builder()
                .url("$baseUrl/products?page=$page&page-size=$PAGE_SIZE")
                .build()
        val result = client.newCall(request).execute().body?.string()
        return convertJsonToList(
            result ?: "",
            CartableProduct::class.java,
        )
    }

    override fun fetchProduct(id: Long): CartableProduct {
        val request =
            Request.Builder()
                .url("$baseUrl/product?id=$id")
                .build()
        return convertJsonToObject(
            client.newCall(request).execute().body?.string() ?: "",
            CartableProduct::class.java,
        )
    }

    companion object {
        private const val BASE_URL = "http://localhost:12345/"
        private const val PAGE_SIZE = 20
    }
}
