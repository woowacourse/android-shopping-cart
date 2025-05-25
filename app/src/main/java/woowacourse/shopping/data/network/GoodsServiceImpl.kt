package woowacourse.shopping.data.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.entity.GoodsEntity
import woowacourse.shopping.data.service.GoodsService

class GoodsServiceImpl : GoodsService {
    override fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<GoodsEntity> {
        val url =
            BASE_URL.toHttpUrl()
                .newBuilder()
                .addPathSegment("products")
                .addPathSegment("page")
                .addQueryParameter(PARAM_PAGE, page.toString())
                .addQueryParameter(PARAM_COUNT, count.toString())
                .build()

        val body = executeRequest(url)
        return try {
            gson.fromJson(body, Array<GoodsEntity>::class.java).toList()
        } catch (e: JsonSyntaxException) {
            Log.e("GoodsServiceImpl", "JSON 파싱 실패: ${e.message}")
            emptyList()
        }
    }

    override fun getGoodsById(id: Int): GoodsEntity? {
        val url =
            BASE_URL.toHttpUrl()
                .newBuilder()
                .addPathSegment("products")
                .addQueryParameter(PARAM_ID, id.toString())
                .build()

        val body = executeRequest(url)
        return try {
            gson.fromJson(body, GoodsEntity::class.java)
        } catch (e: JsonSyntaxException) {
            Log.e("GoodsServiceImpl", "JSON 파싱 실패 (상품 ID: $id): ${e.message}")
            null
        }
    }

    private fun executeRequest(url: okhttp3.HttpUrl): String? {
        val request =
            requestBuilder
                .url(url)
                .build()

        return try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    Log.e("GoodsDaoImpl", "요청 실패: ${response.code}")
                    null
                } else {
                    response.body?.string()?.trim()
                }
            }
        } catch (e: Exception) {
            Log.e("GoodsDaoImpl", "요청 예외 발생: ${e.message}")
            null
        }
    }

    companion object {
        private val requestBuilder = Request.Builder()
        private val gson = Gson()
        private val client = OkHttpClient()
        private const val BASE_URL: String = "http://localhost:8888"

        private const val PARAM_ID: String = "id"
        private const val PARAM_PAGE: String = "page"
        private const val PARAM_COUNT: String = "count"
    }
}
