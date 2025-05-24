package woowacourse.shopping.data.client

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.view.uimodel.ProductUiModel

abstract class HttpClientProductDataSource {
    private val parser = Gson()
    private val client = OkHttpClient()

    protected open val findUrl: String = ""
    protected open val totalSizeUrl: String = ""

    fun findAll(pageRequest: PageRequest): Page<ProductUiModel> {
        val url =
            findUrl.toHttpUrl()
                .newBuilder()
                .addQueryParameter("page", pageRequest.requestPage.toString())
                .addQueryParameter("size", pageRequest.pageSize.toString())
                .build()

        val request =
            Request.Builder()
                .url(url)
                .get()
                .build()

        val items =
            runCatching {
                val response = client.newCall(request).execute()

                if (!response.isSuccessful) {
                    return pageRequest.toPage(emptyList(), totalSize())
                }

                response.body?.string()?.let {
                    val productListType = object : TypeToken<List<ProductUiModel>>() {}.type
                    parser.fromJson<List<ProductUiModel>>(it, productListType)
                } ?: emptyList()
            }.getOrNull() ?: emptyList()

        return pageRequest.toPage(items, totalSize())
    }

    fun totalSize(): Int {
        val request =
            Request.Builder()
                .url(totalSizeUrl)
                .get()
                .build()

        val size =
            runCatching {
                val response = client.newCall(request).execute()
                if (!response.isSuccessful) {
                    return 0
                }
                response.body?.string()?.toIntOrNull()
            }.getOrNull() ?: 0
        return size
    }
}
