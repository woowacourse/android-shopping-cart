package woowacourse.shopping.data.network

import android.util.Log
import com.google.gson.Gson
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import woowacourse.shopping.data.entity.GoodsEntity
import woowacourse.shopping.data.service.GoodsService
import java.io.IOException

class GoodsServiceImpl : GoodsService {
    override fun getPagedGoods(
        page: Int,
        count: Int,
    ): Result<List<GoodsEntity>> {
        val url =
            BASE_URL.toHttpUrl()
                .newBuilder()
                .addPathSegment("products")
                .addPathSegment("page")
                .addQueryParameter(PARAM_PAGE, page.toString())
                .addQueryParameter(PARAM_COUNT, count.toString())
                .build()

        return runCatching {
            val body = executeRequest(url)
            gson.fromJson(body, Array<GoodsEntity>::class.java).toList()
        }
    }

    override fun getGoodsById(id: Int): Result<GoodsEntity?> {
        val url =
            BASE_URL.toHttpUrl()
                .newBuilder()
                .addPathSegment("products")
                .addQueryParameter(PARAM_ID, id.toString())
                .build()

        return runCatching {
            val body = executeRequest(url)
            gson.fromJson(body, GoodsEntity::class.java)
        }
    }

    override fun getGoodsListByIds(id: List<Int>): Result<List<GoodsEntity>> {
        val url =
            BASE_URL.toHttpUrl()
                .newBuilder()
                .addPathSegment("products")
                .addPathSegment("ids")
                .addQueryParameter(PARAM_ID, id.joinToString())
                .build()

        return kotlin.runCatching {
            val body = executeRequest(url)
            gson.fromJson(body, Array<GoodsEntity>::class.java).toList()
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
                    val message = "요청 실패: ${response.code}"
                    Log.e(TAG, message)
                    throw IOException(message)
                } else {
                    response.body?.string()?.trim()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "요청 예외 발생: ${e.message}")
            throw e
        }
    }

    companion object {
        private val requestBuilder = Request.Builder()
        private val gson = Gson()
        private val client = OkHttpClient()
        private const val BASE_URL: String = "http://localhost:8888"

        private const val TAG: String = "GoodsDaoImpl"
        private const val PARAM_ID: String = "id"
        private const val PARAM_PAGE: String = "page"
        private const val PARAM_COUNT: String = "count"
    }
}
