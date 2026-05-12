package woowacourse.shopping.repository.http

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.model.Products
import woowacourse.shopping.repository.ProductRepository
import java.io.IOException

class HttpProductRepository(
    private val client: OkHttpClient,
    private val baseUrlProvider: () -> HttpUrl,
) : ProductRepository {
    constructor(
        client: OkHttpClient,
        baseUrl: String,
    ) : this(
        client = client,
        baseUrlProvider = {
            requireNotNull(baseUrl.toHttpUrlOrNull()) { "유효한 상품 API baseUrl이 필요합니다." }
        },
    )

    @Volatile
    private var cachedProducts: List<Product> = emptyList()

    override val size: Int
        get() = cachedProducts.size

    override suspend fun getProducts(
        fromIndex: Int,
        limit: Int,
    ): Products =
        withContext(Dispatchers.IO) {
            val allProducts = getCachedOrFetchProducts()
            val safeFrom = fromIndex.coerceIn(0, allProducts.size)
            val safeLimit = limit.coerceAtLeast(0)
            val safeTo = minOf(safeFrom + safeLimit, allProducts.size)

            Products(allProducts.subList(safeFrom, safeTo))
        }

    override suspend fun hasNext(current: Int): Boolean =
        withContext(Dispatchers.IO) {
            val allProducts = getCachedOrFetchProducts()
            current < allProducts.lastIndex
        }

    override suspend fun findAllByIds(ids: Set<ProductId>): Map<ProductId, Product> =
        withContext(Dispatchers.IO) {
            if (ids.isEmpty()) return@withContext emptyMap()

            val cachedProductsById = cachedProducts.associateBy { it.id }.toMutableMap()
            val missingIds = ids - cachedProductsById.keys

            missingIds.forEach { productId ->
                fetchProductById(productId)?.let { product ->
                    cachedProductsById[product.id] = product
                }
            }

            ids
                .mapNotNull { productId ->
                    cachedProductsById[productId]?.let { productId to it }
                }.toMap()
        }

    private fun fetchAllProducts(): List<Product> {
        val body = executeRequest(pathSegments = listOf("products"))

        val products =
            runCatching {
                val jsonArray = JSONArray(body)
                List(jsonArray.length()) { index ->
                    ProductResponseDto.fromJson(jsonArray.getJSONObject(index)).toDomain()
                }
            }.getOrElse { throwable ->
                throw ProductParsingException("상품 목록 응답을 파싱할 수 없습니다.", throwable)
            }

        cachedProducts = products
        return products
    }

    private fun getCachedOrFetchProducts(): List<Product> {
        if (cachedProducts.isNotEmpty()) return cachedProducts

        return fetchAllProducts()
    }

    private fun fetchProductById(productId: ProductId): Product? {
        val remoteId = productId.toRemoteIdOrNull() ?: return null
        val body = executeRequest(pathSegments = listOf("products", remoteId.toString()))

        val product =
            runCatching {
                ProductResponseDto.fromJson(JSONObject(body)).toDomain()
            }.getOrElse { throwable ->
                throw ProductParsingException("상품 상세 응답을 파싱할 수 없습니다.", throwable)
            }

        cachedProducts = (cachedProducts + product).distinctBy { it.id }
        return product
    }

    private fun executeRequest(pathSegments: List<String>): String {
        val url =
            baseUrlProvider()
                .newBuilder()
                .apply {
                    pathSegments.forEach { addPathSegment(it) }
                }.build()

        val request =
            Request
                .Builder()
                .url(url)
                .get()
                .build()

        return try {
            client.newCall(request).execute().use { response ->
                val responseBody = response.body.string()

                if (!response.isSuccessful) {
                    throw ProductResponseException(
                        code = response.code,
                        message = "상품 API 호출에 실패했습니다. code=${response.code}",
                    )
                }

                require(!responseBody.isNullOrBlank()) { "상품 API 응답 본문이 비어 있습니다." }
                responseBody
            }
        } catch (exception: ProductRemoteException) {
            throw exception
        } catch (exception: IOException) {
            throw ProductNetworkException("상품 API 네트워크 호출에 실패했습니다.", exception)
        } catch (exception: IllegalArgumentException) {
            throw ProductParsingException("상품 API 응답이 올바르지 않습니다.", exception)
        }
    }
}
