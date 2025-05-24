package woowacourse.shopping.data.network

import android.util.Log
import com.google.gson.Gson
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.dao.GoodsDao
import woowacourse.shopping.data.entity.GoodsEntity

class GoodsDaoImpl : GoodsDao {
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
        return gson.fromJson(body, Array<GoodsEntity>::class.java).toList()
    }

    override fun getGoodsById(id: Int): GoodsEntity {
        val url =
            BASE_URL.toHttpUrl()
                .newBuilder()
                .addPathSegment("products")
                .addQueryParameter(PARAM_ID, id.toString())
                .build()

        val body = executeRequest(url)
        return gson.fromJson(body, GoodsEntity::class.java)
    }

    private fun executeRequest(url: okhttp3.HttpUrl): String? {
        val request =
            Request.Builder()
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
        private val gson = Gson()
        private val client = OkHttpClient()
        private const val BASE_URL: String = "http://localhost:8888"

        private const val PARAM_ID: String = "id"
        private const val PARAM_PAGE: String = "page"
        private const val PARAM_COUNT: String = "count"
    }
}
