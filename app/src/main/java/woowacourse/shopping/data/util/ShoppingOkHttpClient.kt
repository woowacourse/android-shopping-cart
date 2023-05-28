package woowacourse.shopping.data.util

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import woowacourse.shopping.domain.Product
import java.io.IOException
import java.lang.Exception

object ShoppingOkHttpClient : OkHttpClient() {
    fun enqueue(
        url: String,
        onSuccess: (List<Product>) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        newCall(buildRequest(url)).enqueue(
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onFailure(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    response.close()

                    val result = responseBody?.let {
                        responseBody.convertJsonToProducts()
                    } ?: emptyList()

                    onSuccess(result)
                }
            },
        )
    }

    private fun buildRequest(url: String): Request {
        return Request.Builder()
            .url(url)
            .build()
    }
}
