package woowacourse.shopping.data.network.product

import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import woowacourse.shopping.domain.Product

class ProductDaoImpl(
    private val client: OkHttpClient,
    private val baseUrl: HttpUrl,
    private val json: Json,
) : ProductDao {
    override suspend fun findAllProduct(
        startIndex: Int,
        pageSize: Int,
    ): List<Product> {
        val url = baseUrl.newBuilder()
            .addPathSegment("products")
            .addQueryParameter("startIndex", startIndex.toString())
            .addQueryParameter("pageSize", pageSize.toString())
            .build()

        val request = Request.Builder().url(url).build()
        val call = client.newCall(request)

        return suspendCancellableCoroutine { continuation ->
            continuation.invokeOnCancellation { call.cancel() }
            call.enqueue(
                object : Callback {
                    override fun onResponse(
                        call: Call,
                        response: Response,
                    ) {
                        response.use {
                            runCatching {
                                check(it.isSuccessful) { "products 요청 실패: ${it.code}" }
                                val body = it.body?.string().orEmpty()
                                json.decodeFromString<List<ProductDto>>(body)
                                    .map(ProductDto::toDomain)
                            }.fold(
                                onSuccess = { result -> continuation.resume(result) },
                                onFailure = { e -> continuation.resumeWithException(e) },
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call,
                        e: IOException,
                    ) {
                        continuation.resumeWithException(e)
                    }
                },
            )
        }
    }

    override suspend fun findById(id: String): Product = withContext(Dispatchers.IO) {
        val url = baseUrl.newBuilder()
            .addPathSegment("product")
            .addQueryParameter("id", id)
            .build()

        val request = Request.Builder().url(url).build()
        client.newCall(request).execute().use { response ->
            check(response.isSuccessful) { "products 요청 실패: ${response.code}" }

            val body = response.body?.string().orEmpty()
            json.decodeFromString<ProductDto>(body).toDomain()
        }
    }
}
