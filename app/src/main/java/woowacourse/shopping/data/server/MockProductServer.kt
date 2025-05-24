package woowacourse.shopping.data.server

import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import woowacourse.shopping.data.dao.ProductDao

class MockProductServer(
    private val productDao: ProductDao,
) {
    fun handleRequest(url: HttpUrl): Response {
        val lastId = url.queryParameter("lastId")?.toIntOrNull() ?: 0
        val count = url.queryParameter("count")?.toIntOrNull() ?: 10

        val products = productDao.getNextProducts(lastId, count)

        val json = Gson().toJson(products)
        return Response
            .Builder()
            .code(SUCCESS_CODE)
            .protocol(Protocol.HTTP_1_1)
            .message(SUCCESS_MESSAGE)
            .request(Request.Builder().url(url).build())
            .body(
                json.toResponseBody("application/json".toMediaTypeOrNull()),
            ).build()
    }

    companion object {
        private const val SUCCESS_CODE = 200
        private const val SUCCESS_MESSAGE = "OK"
    }
}
