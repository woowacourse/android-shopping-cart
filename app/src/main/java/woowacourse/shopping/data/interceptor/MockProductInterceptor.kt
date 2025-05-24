package woowacourse.shopping.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import woowacourse.shopping.data.server.MockProductServer

class MockProductInterceptor(
    private val server: MockProductServer,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url

        if (url.encodedPath == "/products") {
            return server.handleRequest(url)
        }

        return chain.proceed(request)
    }
}
