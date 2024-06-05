package woowacourse.shopping.remote.logging

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        logRequest(request, chain)

        val response: Response = chain.proceed(request)
        logResponse(response)

        return response
    }

    private fun logRequest(
        request: Request,
        chain: Interceptor.Chain,
    ) {
        println(
            """
            REQUEST
            request: $request
            url: ${request.url}
            chain, headers:  ${chain.connection()}${request.headers}
            """.trimIndent(),
        )
    }

    private fun logResponse(response: Response) {
        println(
            """
            RESPONSE
            response: $response
            url: ${response.request.url}
            code: ${response.code}
            header: ${response.headers}
            """.trimIndent(),
        )
    }
}
