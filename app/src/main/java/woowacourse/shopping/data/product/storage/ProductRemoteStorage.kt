package woowacourse.shopping.data.product.storage

import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.common.convertJsonToList
import woowacourse.shopping.data.common.convertJsonToObject
import woowacourse.shopping.data.product.remote.MockProductServer
import woowacourse.shopping.data.product.remote.dto.ProductResponseDto
import kotlin.concurrent.thread

class ProductRemoteStorage : ProductRemoteDataStorage {
    private val mockProductServer = MockProductServer()
    private val client = OkHttpClient()

    init {
        thread {
            mockProductServer.start(12345)
        }
    }

    override fun load(
        lastProductId: Long?,
        size: Int,
    ): List<ProductResponseDto> {
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
            ProductResponseDto::class.java,
        )
    }

    override fun getProductsById(productId: Long): ProductResponseDto {
        val request =
            Request
                .Builder()
                .url("$BASE_URL/products/$productId")
                .build()
        val result =
            client
                .newCall(request)
                .execute()
                .body
                ?.string()
        return convertJsonToObject(
            result ?: "",
            ProductResponseDto::class.java,
        )
    }

    companion object {
        private const val BASE_URL = "http://localhost:12345"
    }
}
