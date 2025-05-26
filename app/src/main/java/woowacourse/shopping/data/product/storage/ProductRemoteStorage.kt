package woowacourse.shopping.data.product.storage

import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.common.convertJsonToList
import woowacourse.shopping.data.product.local.entity.ProductEntity
import woowacourse.shopping.data.product.remote.MockWebServer

class ProductRemoteStorage : ProductsStorage {
    private val mockWebServer = MockWebServer()
    private val client = OkHttpClient()

    init {
        mockWebServer.startMockWebServer(12345)
    }

    override fun load(
        lastProductId: Long?,
        size: Int,
    ): List<ProductEntity> {
        val request =
            Request
                .Builder()
                .url("$BASE_URL/products?lastProductId=$lastProductId&size=$size")
                .build()
        val result =
            client
                .newCall(request)
                .execute()
                .body
                ?.string()
        return convertJsonToList(
            result ?: "",
            ProductEntity::class.java,
        )
    }

    override fun getRecentWatching(size: Int): List<ProductEntity> {
        val request =
            Request
                .Builder()
                .url("$BASE_URL/products/recentWatching?size=$size")
                .build()
        val result =
            client
                .newCall(request)
                .execute()
                .body
                ?.string()
        return convertJsonToList(
            result ?: "",
            ProductEntity::class.java,
        )
    }

    companion object {
        private const val BASE_URL = "http://localhost:12345"
    }
}
