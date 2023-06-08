package woowacourse.shopping.data.remote

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import woowacourse.shopping.data.remote.ProductMockServer.Companion.PORT

object NetworkModule {
    private const val BASE_URL = "http://localhost:$PORT"
    private val okHttpClient = OkHttpClient()

    fun getService(path: String): Call {
        val request = Request.Builder().url(BASE_URL + path).build()
        return okHttpClient.newCall(request)
    }

    fun postService(path: String, requestBody: RequestBody?): Call {
        val request = Request.Builder()
            .url(BASE_URL + path)
            .post(requireNotNull(requestBody))
            .build()

        return okHttpClient.newCall(request)
    }
}
