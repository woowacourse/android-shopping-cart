package woowacourse.shopping.data.client

import com.google.gson.reflect.TypeToken
import okhttp3.HttpUrl.Companion.toHttpUrl
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.view.uimodel.ProductUiModel

abstract class HttpClientProductDataSource(
    private val client: HttpClient,
    private val parser: JsonParser,
) {
    abstract val findUrl: String
    abstract val totalSizeUrl: String

    fun findAll(pageRequest: PageRequest): Page<ProductUiModel> {
        val url =
            findUrl.toHttpUrl()
                .newBuilder()
                .addQueryParameter("page", pageRequest.requestPage.toString())
                .addQueryParameter("size", pageRequest.pageSize.toString())
                .build()

        val request =
            RequestImpl(
                url,
                HttpMethod.GET,
            )
        val items =
            runCatching {
                val response = client.request(request)
                val productListType = object : TypeToken<List<ProductUiModel>>() {}.type
                parser.fromJson<List<ProductUiModel>>(response.body, productListType)
            }.getOrNull() ?: emptyList()

        return pageRequest.toPage(items, totalSize())
    }

    fun totalSize(): Int {
        val request =
            RequestImpl(
                totalSizeUrl.toHttpUrl(),
                HttpMethod.GET,
            )

        val size =
            runCatching {
                client.request(request).body.toIntOrNull()
            }.getOrNull() ?: 0
        return size
    }
}
