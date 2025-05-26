package woowacourse.shopping.data.product.storage

import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.common.convertJsonToList
import woowacourse.shopping.data.product.local.entity.ProductEntity
import woowacourse.shopping.data.product.remote.MockProductServer

class ProductRemoteStorage : ProductsStorage {
    private val mockProductServer = MockProductServer()
    private val client = OkHttpClient()

    init {
        mockProductServer.start(12345)
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

    companion object {
        private const val BASE_URL = "http://localhost:12345"
    }
}
