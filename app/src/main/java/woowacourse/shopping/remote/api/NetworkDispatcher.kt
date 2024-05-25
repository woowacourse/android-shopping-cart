package woowacourse.shopping.remote.api

import com.google.gson.Gson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.remote.api.ApiClient.CONTENT_KEY
import woowacourse.shopping.remote.api.ApiClient.CONTENT_TYPE
import woowacourse.shopping.remote.api.ApiClient.GET_FIND_PRODUCT_PATH
import woowacourse.shopping.remote.api.ApiClient.GET_PAGING_PRODUCT_PATH
import kotlin.math.min

class NetworkDispatcher(private val errorListener: ErrorListener) : Dispatcher() {
    private val gson = Gson()

    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.path ?: return MockResponse().setResponseCode(404)

        return when {
            path.startsWith(GET_FIND_PRODUCT_PATH) -> {
                val productId = path.removePrefix(GET_FIND_PRODUCT_PATH).toLong()
                val body =
                    DummyData.PRODUCT_LIST.find { it.id == productId }
                        ?: throw NoSuchElementException()

                MockResponse()
                    .setHeader(CONTENT_TYPE, CONTENT_KEY)
                    .setResponseCode(200)
                    .setBody(gson.toJson(body))
            }

            path.startsWith(GET_PAGING_PRODUCT_PATH) -> {
                val segments = path.removePrefix(GET_PAGING_PRODUCT_PATH).split("/")
                if (segments.size == 2) {
                    val page = segments[0].toIntOrNull()
                    val pageSize = segments[1].toIntOrNull()

                    if (page != null && pageSize != null) {
                        val fromIndex = page * pageSize
                        val toIndex = min(fromIndex + pageSize, DummyData.PRODUCT_LIST.size)
                        val body = DummyData.PRODUCT_LIST.subList(fromIndex, toIndex)

                        MockResponse()
                            .setHeader(CONTENT_TYPE, CONTENT_KEY)
                            .setResponseCode(200)
                            .setBody(gson.toJson(body))
                    } else {
                        errorListener.handleNetworkError()
                        MockResponse().setResponseCode(404)
                    }
                } else {
                    errorListener.handleNetworkError()
                    MockResponse().setResponseCode(404)
                }
            }

            else -> {
                errorListener.handleNetworkError()
                MockResponse().setResponseCode(404)
            }
        }
    }
}
