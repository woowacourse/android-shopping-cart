package woowacourse.shopping.data.repository.product

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.domain.Product
import woowacourse.shopping.mapper.toProduct
import woowacourse.shopping.view.uimodel.ProductUiModel

abstract class HttpClientProductRepository : ProductsRepository {
    private val parser = Gson()
    protected open val findUrl: String = ""
    protected open val totalSizeUrl: String = ""

    override fun findAll(pageRequest: PageRequest): Page<Product> {
        val client = OkHttpClient()
        val request =
            Request.Builder()
                .url(findUrl + queryParameter(pageRequest))
                .get()
                .build()

        var temp: List<ProductUiModel>? = null
        runCatching {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.string()?.let {
                    val productListType = object : TypeToken<List<ProductUiModel>>() {}.type
                    temp = parser.fromJson(it, productListType)
                }
            }
        }
        val items = temp?.map { it.toProduct() }?.toList() ?: emptyList()

        return pageRequest.toPage(items, totalSize())
    }

    override fun totalSize(): Int {
        val client = OkHttpClient()
        val request =
            Request.Builder()
                .url(totalSizeUrl)
                .get()
                .build()

        var size: Int? = null
        runCatching {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                size = response.body?.string()?.toIntOrNull()
            }
        }
        return size ?: 0
    }

    companion object {
        private fun queryParameter(pageRequest: PageRequest) = "?page=${pageRequest.requestPage}&size=${pageRequest.pageSize}"
    }
}
