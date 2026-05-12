package woowacourse.shopping.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.remote.MockServer
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.ProductItems
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.math.min

object ProductRepositoryImpl : ProductRepository {
    private val client = OkHttpClient()
    private val json = Json { 
        ignoreUnknownKeys = true 
        encodeDefaults = true
    }

    private suspend fun fetchAllProducts(): List<Product> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("${MockServer.getBaseUrl()}products")
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext emptyList()
                }
                val body = response.body?.string() ?: return@withContext emptyList()
                json.decodeFromString<List<Product>>(body)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getProducts(
        page: Int,
        pageSize: Int,
    ): ProductItems {
        require(page >= 0) { "페이지 번호는 0보다 크거나 같은 정수여야 합니다." }
        require(pageSize >= 1) { "페이지 사이즈는 1보다 큰 정수여야 합니다." }

        val allProducts = fetchAllProducts()
        val fromIndex = page * pageSize
        if (fromIndex >= allProducts.size) {
            return ProductItems(emptyList())
        }

        val toIndex = min(fromIndex + pageSize, allProducts.size)
        val result = allProducts.subList(fromIndex, toIndex)
        return ProductItems(result)
    }

    override suspend fun getProductCount(): Int = fetchAllProducts().size

    override suspend fun getProduct(id: String): Product? = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("${MockServer.getBaseUrl()}products/$id")
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext null
                val body = response.body?.string() ?: return@withContext null
                json.decodeFromString<Product>(body)
            }
        } catch (e: Exception) {
            null
        }
    }
}
