package woowacourse.shopping.remote.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HttpExceptionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (!response.isSuccessful) {
            handleHttpException(response.code)
        }

        return response
    }

    private fun handleHttpException(code: Int) {
        when (code) {
            400 -> throw IllegalArgumentException("Bad Request (400)")
            401 -> throw SecurityException("Unauthorized (401)")
            403 -> throw SecurityException("Forbidden (403)")
            404 -> throw NoSuchElementException("Not Found (404)")
            500 -> throw IOException("Internal Server Error (500)")
            else -> throw Exception("Unknown HTTP error code: $code")
        }
    }
}
