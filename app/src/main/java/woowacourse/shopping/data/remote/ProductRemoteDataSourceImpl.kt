package woowacourse.shopping.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import woowacourse.shopping.BuildConfig
import woowacourse.shopping.data.remote.dto.ProductDto
import woowacourse.shopping.data.remote.dto.ProductsResponseDto

class ProductRemoteDataSourceImpl(
    private val baseUrl: String,
) : ProductRemoteDataSource {
    private val client = OkHttpClient
        .Builder()
        .apply {
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                addInterceptor(loggingInterceptor)
            }
        }.build()

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getProducts(
        page: Int,
        pageSize: Int,
    ): ProductsResponseDto =
        withContext(Dispatchers.IO) {
            val request = Request
                .Builder()
                .url("$baseUrl/products?page=$page&pageSize=$pageSize")
                .get()
                .build()
            val body = client
                .newCall(request)
                .execute()
                .use { checkNotNull(it.body).string() }

            json.decodeFromString<ProductsResponseDto>(body)
        }

    override suspend fun getProductById(id: String): ProductDto =
        withContext(Dispatchers.IO) {
            val request = Request
                .Builder()
                .url("$baseUrl/products/$id")
                .get()
                .build()
            val body = client
                .newCall(request)
                .execute()
                .use { checkNotNull(it.body).string() }
            json.decodeFromString<ProductDto>(body)
        }

    override suspend fun getProductsByIds(ids: List<String>): List<ProductDto> =
        withContext(Dispatchers.IO) {
            val request = Request
                .Builder()
                .url("$baseUrl/products?ids=${ids.joinToString(",")}")
                .get()
                .build()

            val body = client
                .newCall(request)
                .execute()
                .use { checkNotNull(it.body).string() }
            json.decodeFromString<ProductsResponseDto>(body).products
        }
}
