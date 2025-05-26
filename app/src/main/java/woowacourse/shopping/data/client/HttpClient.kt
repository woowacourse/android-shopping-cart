package woowacourse.shopping.data.client

import android.content.ContentProviderOperation.newCall
import okhttp3.OkHttpClient
import okhttp3.Request as okRequest

interface HttpClient {
    fun request(request: Request): Response {
        val okhttpRequest =
            when (request.method) {
                HttpMethod.GET -> {
                    okRequest.Builder()
                        .url(request.url)
                        .get()
                        .build()
                }
            }
        val client = OkHttpClient()
        val response = client.newCall(okhttpRequest).execute()
        return response.body?.string()?.let {
            ResponseImpl(it)
        } ?: throw IllegalStateException("Response body is null")
    }
}
