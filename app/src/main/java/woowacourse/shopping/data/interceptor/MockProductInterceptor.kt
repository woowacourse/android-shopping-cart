package woowacourse.shopping.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.server.MockProductServer

class MockProductInterceptor(
    productDao: ProductDao,
) : Interceptor {
    private val server = MockProductServer(productDao)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url

        if (url.encodedPath == "/products") {
            return server.handleRequest(url)
        }

        return chain.proceed(request)
    }
}
