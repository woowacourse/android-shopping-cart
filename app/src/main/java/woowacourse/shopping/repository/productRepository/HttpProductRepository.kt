package woowacourse.shopping.repository.productRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.remote.ProductDto
import woowacourse.shopping.data.remote.toDomain
import woowacourse.shopping.domain.Product
import java.util.UUID

class HttpProductRepository(
    private val baseUrl: String,
    private val client: OkHttpClient = OkHttpClient(),
) : ProductRepository {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getProducts(page: Int, pageSize: Int): List<Product> =
        withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url("${baseUrl.removeSuffix("/")}/products")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext emptyList()
                val body = response.body?.string() ?: return@withContext emptyList()
                val productDtos = json.decodeFromString<List<ProductDto>>(body)

                val fromIndex = (page * pageSize).coerceAtMost(productDtos.size)
                val toIndex = (fromIndex + pageSize).coerceAtMost(productDtos.size)

                productDtos.subList(fromIndex, toIndex).map { it.toDomain() }
            }
        }

    override fun getProductById(id: UUID): Product? = runBlocking {
        getProducts(0, 20).find { it.productId == id }
    }
}
