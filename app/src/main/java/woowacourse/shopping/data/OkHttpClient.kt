package woowacourse.shopping.data

import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class OkHttpClient {
    private val okHttpClient = OkHttpClient()
    private val requestBuilder = Request.Builder()

    fun request(url: HttpUrl, response: (Response) -> Unit) {
        val request = requestBuilder.url(url).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) = Unit

            override fun onResponse(call: Call, response: Response) {
                response(response)
            }
        })
    }
}
