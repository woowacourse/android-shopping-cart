package woowacourse.shopping.data.source.remote

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import woowacourse.shopping.data.source.remote.dto.ProductResponse
import woowacourse.shopping.data.source.remote.mock.MockServer
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ProductRemoteDataSource(
    private val client: OkHttpClient = HttpClient.instance,
    private val baseUrlProvider: suspend () -> String = { MockServer.baseUrl() },
    private val json: Json = Json { ignoreUnknownKeys = true },
) {
    suspend fun fetchProducts(
        offset: Int,
        limit: Int,
    ): List<ProductResponse> {
        val url =
            baseUrlProvider()
                .toHttpUrl()
                .newBuilder()
                .addPathSegment("products")
                .addQueryParameter("offset", offset.toString())
                .addQueryParameter("limit", limit.toString())
                .build()
        val body = execute(Request.Builder().url(url).build())
        return json.decodeFromString(ListSerializer(ProductResponse.serializer()), body)
    }

    suspend fun fetchProductById(id: Long): ProductResponse {
        val url =
            baseUrlProvider()
                .toHttpUrl()
                .newBuilder()
                .addPathSegment("products")
                .addPathSegment(id.toString())
                .build()

        val body = execute(Request.Builder().url(url).build())
        return json.decodeFromString(ProductResponse.serializer(), body)
    }

    suspend fun fetchProductsByIds(ids: List<Long>): List<ProductResponse> {
        if (ids.isEmpty()) return emptyList()
        val url =
            baseUrlProvider()
                .toHttpUrl()
                .newBuilder()
                .addPathSegment("products")
                .addQueryParameter("ids", ids.joinToString(","))
                .build()
        val body = execute(Request.Builder().url(url).build())
        return json.decodeFromString(ListSerializer(ProductResponse.serializer()), body)
    }

    private suspend fun execute(request: Request): String =
        suspendCancellableCoroutine { cont ->
            val call = client.newCall(request)
            cont.invokeOnCancellation { runCatching { call.cancel() } }
            call.enqueue(
                object : Callback {
                    override fun onFailure(
                        call: Call,
                        e: IOException,
                    ) {
                        cont.resumeWithException(e)
                    }

                    override fun onResponse(
                        call: Call,
                        response: Response,
                    ) {
                        response.use {
                            if (!it.isSuccessful) {
                                cont.resumeWithException(IOException("HTTP ${it.code}"))
                                return
                            }
                            val body = it.body?.string().orEmpty()
                            cont.resume(body)
                        }
                    }
                },
            )
        }
}
